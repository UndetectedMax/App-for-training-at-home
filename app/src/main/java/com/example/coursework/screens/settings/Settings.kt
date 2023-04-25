package com.example.coursework.screens.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.databinding.FragmentSettingsBinding

class Settings : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
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
}


