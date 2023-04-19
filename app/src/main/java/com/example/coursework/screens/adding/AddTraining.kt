package com.example.coursework.screens.adding


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.coursework.R
import com.example.coursework.databinding.FragmentAddtrainingBinding

class AddTraining : Fragment() {
    private lateinit var binding: FragmentAddtrainingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddtrainingBinding.inflate(layoutInflater, container, false)

        binding.addTrainByCode.setOnClickListener {
            val args = Bundle()
            findNavController().navigate(
                R.id.action_add_training_icon_to_addTrainingByCode,args
            )
        }

        binding.addOwnTrainingButton.setOnClickListener {
            val args = Bundle()
            findNavController().navigate(
                R.id.action_add_training_icon_to_addOwnTraining,args
            )
        }
        return binding.root
    }
}