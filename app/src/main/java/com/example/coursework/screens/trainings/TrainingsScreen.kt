package com.example.coursework.screens.trainings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.databinding.FragmentTrainingsScreenBinding

class TrainingsScreen : Fragment() {
    private lateinit var binding: FragmentTrainingsScreenBinding
    private var list = arrayListOf(
        "Cardio",
        "Trainings for arms",
        "Trainings for legs",
        "Complex trainings"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingsScreenBinding.inflate(layoutInflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = TrainingAdapter(list) { text ->
            val intent = Intent(this.requireContext(), TrainDetails::class.java)
            intent.putExtra("text", text)
            startActivity(intent)
        }
        return binding.root
    }


}

