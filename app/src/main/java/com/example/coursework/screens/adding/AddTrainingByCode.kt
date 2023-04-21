package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.databinding.FragmentAddTrainingByCodeBinding



class AddTrainingByCode : Fragment() {
    private lateinit var binding: FragmentAddTrainingByCodeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTrainingByCodeBinding.inflate(layoutInflater, container, false)

        binding.submitButton.setOnClickListener {
            if (binding.enterCodeText.text != null) {
                val message = binding.enterCodeText.text.toString()
                binding.textView.text = "You have chosen the training with code: $message"
            }
        }
        return binding.root
    }
}