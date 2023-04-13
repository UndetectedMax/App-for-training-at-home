package com.example.coursework.screens.trainings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.R
import com.example.coursework.databinding.FragmentTrainingsScreenBinding

class TrainingsScreen : Fragment() {

    private lateinit var binding: FragmentTrainingsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrainingsScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}

