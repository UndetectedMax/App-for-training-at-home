package com.example.coursework.screens.trainings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.databinding.FragmentTrainingsScreenBinding

class TrainingsScreen : Fragment() {

    private lateinit var binding: FragmentTrainingsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingsScreenBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.findViewById<RecyclerView>(R.id.recyclerView)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = RVAdaptor(fetchData())
        return binding.root
    }

    private fun fetchData(): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0 until 15)
            list.add("Exercise $i")
        return list
    }
}

