package com.example.coursework.screens.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.databinding.FragmentSettingsBinding
import com.example.coursework.repositories.UserInfo
import com.example.coursework.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso

class Settings : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

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
                "Change application theme",
                "Contact us"
            )
        )
        val recyclerView: RecyclerView = binding.settingsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = SettingsAdapter(settingsItem, this.requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*ToDO: добавь прогресс бар,который тут будет визибл*/
        FirebaseAuth.getInstance().currentUser?.let {
            repository.getCurrentUserById(it, object : UserRepository.FetchSportsmanListener {
                override fun onFetchSportsman(user: UserInfo) {
                    currentUser = user
                    showDetails(currentUser, it)
                }
            })
        }
        /*ToDO:а тут снова инвизибл */
    }

    private fun showDetails(currentUser: UserInfo, it: FirebaseUser) {
        binding.name.text = currentUser.displayName.toString()
        binding.email.text = it.email
        currentUser.photo?.let {
            if (it.isNotEmpty()) {
                Picasso.get().load(currentUser.photo.toString()).into(binding.userPhoto)
            }
        }
        binding.logoutButton.setOnClickListener {

        }
    }
}


