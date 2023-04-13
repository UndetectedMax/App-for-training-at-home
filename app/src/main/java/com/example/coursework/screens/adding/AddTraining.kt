package com.example.coursework.screens.adding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.databinding.FragmentAddtrainingBinding

class AddTraining : Fragment() {
    private lateinit var binding: FragmentAddtrainingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddtrainingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}