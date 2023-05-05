package com.example.coursework.screens.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.databinding.FragmentSettingsBinding
import com.example.coursework.screens.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Settings : Fragment() {

    private lateinit var firebaseAuth:FirebaseAuth

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)


        firebaseAuth = FirebaseAuth.getInstance()
        val dbUser = firebaseAuth.currentUser
        binding.email.text = dbUser?.email.toString()
        binding.name.text = dbUser?.displayName.toString()


        binding.button3.setOnClickListener {
            firebaseAuth.signOut()
            Toast.makeText(requireContext(), "You have logouted!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity,LoginActivity::class.java))
        }

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
}


