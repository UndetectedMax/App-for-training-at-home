package com.example.coursework.screens.adding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.databinding.FragmentAddTrainingByCodeBinding



class AddTrainingByCode : Fragment() {
    private lateinit var binding: FragmentAddTrainingByCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTrainingByCodeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}