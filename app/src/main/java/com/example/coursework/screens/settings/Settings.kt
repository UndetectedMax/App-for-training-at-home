package com.example.coursework.screens.settings


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.MainActivity
import com.example.coursework.databinding.FragmentSettingsBinding
import com.example.coursework.repositories.User.UserInfo
import com.example.coursework.repositories.User.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class Settings : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val repository = UserRepository()

    private lateinit var currentUser: UserInfo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        val settingsItem = ArrayList<String>(
            listOf(
                "Change the application language",
                "Change application background",
                "Change application theme"
            )
        )
        val recyclerView: RecyclerView = binding.settingsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = SettingsAdapter(settingsItem, this.requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FirebaseAuth.getInstance().currentUser?.let {
            binding.pageProgress.isVisible = true
            repository.getCurrentUserById(it, object : UserRepository.FetchSportsmanListener {
                override fun onFetchSportsman(user: UserInfo) {
                    currentUser = user
                    showDetails(currentUser, it)
                }
            })
        }
        val googleSignInClient =
            GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
        binding.logoutButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("You have pressed the logout button")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton("No,I don`t want", null)
                .setPositiveButton("Yes,I am really sure") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    googleSignInClient.signOut()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                .show()
        }
    }

    private fun showDetails(currentUser: UserInfo, it: FirebaseUser) {
        binding.updateUserName.setText(currentUser.displayName)
        binding.email.text = it.email
        currentUser.photo?.let {
            if (it.isNotEmpty()) {
                Picasso.get().load(currentUser.photo.toString()).into(binding.userPhoto)
            }
        }

        binding.userPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            profilePhotoChooser.launch(intent)
        }

        binding.updateUserPhoto.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.uid?.let {
                updateProfilePhoto(it)
            }
        }

        binding.name.setEndIconOnClickListener {
            updateUserName()
        }

        binding.pageProgress.isVisible = false
    }

    private fun updateUserName() {
        binding.pageProgress.isVisible = true
        if (TextUtils.isEmpty(binding.updateUserName.text)) {
            binding.updateUserName.error = "Name is empty!"
            return
        }
        val request = UserProfileChangeRequest.Builder()
            .setDisplayName(binding.updateUserName.text.toString())
            .build()
        FirebaseAuth.getInstance().currentUser?.updateProfile(request)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    updateDatabaseUser()
                    binding.pageProgress.isVisible = false
                }
            }
    }

    private fun updateProfilePhoto(userId: String) {
        binding.pageProgress.isVisible = true

        val ref: StorageReference = FirebaseStorage.getInstance().reference
            .child("profile_photos/${userId}")

        profilePhotoUri?.let {
            ref.putFile(it).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    updateFirebaseUserPhoto(uri)
                    updateDatabaseUser(uri.toString())
                    binding.pageProgress.isVisible = false
                }
            }
        }
    }

    private fun updateDatabaseUser(path: String = currentUser.photo ?: "") {
        val user = UserInfo(
            displayName = binding.updateUserName.text.toString(),
            uid = FirebaseAuth.getInstance().currentUser!!.uid,
            photo = path
        )
        UserRepository().createOrUpdateUser(user)
    }

    private fun updateFirebaseUserPhoto(uri: Uri) {
        val request = UserProfileChangeRequest.Builder()
            .setPhotoUri(uri)
            .build()
        FirebaseAuth.getInstance().currentUser?.updateProfile(request)
    }

    private var profilePhotoUri: Uri? = null

    private var profilePhotoChooser =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { imageUri ->
                    binding.userPhoto.setImageURI(imageUri.data)
                    profilePhotoUri = imageUri.data
                }
            }
        }
}