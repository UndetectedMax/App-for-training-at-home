package com.example.coursework.screens.adding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.databinding.FragmentAddOwnTrainingBinding


class AddOwnTraining : Fragment() {
    private lateinit var binding: FragmentAddOwnTrainingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddOwnTrainingBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

}