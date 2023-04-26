package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.R
import com.example.coursework.databinding.FragmentAddTrainingByCodeBinding


@SuppressLint("SetTextI18n")
class AddTrainingByCode : Fragment(R.layout.fragment_add_training_by_code) {
    private lateinit var binding: FragmentAddTrainingByCodeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTrainingByCodeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            if (binding.enterCodeText.text != null) {
                val message = binding.enterCodeText.text.toString()
                binding.textView.text = "You have chosen the training with code: $message"
            }
        }
    }
}
