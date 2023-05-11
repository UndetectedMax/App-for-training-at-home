package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coursework.R
import com.example.coursework.databinding.FragmentAddTrainingByCodeBinding
import com.example.coursework.repositories.OwnTrain.TrainRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


@SuppressLint("SetTextI18n")
class AddTrainingByCode : Fragment(R.layout.fragment_add_training_by_code) {
    private lateinit var binding: FragmentAddTrainingByCodeBinding
    private val repository = TrainRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTrainingByCodeBinding.inflate(layoutInflater, container, false)
        val database = FirebaseDatabase.getInstance().getReference("trainings")
        binding.submitButton.setOnClickListener {
            if (binding.enterCodeText.text.isBlank()) {
                Toast.makeText(requireContext(),"You need to enter code in the required field",Toast.LENGTH_SHORT).show()
            } else {
                val trainCode = binding.enterCodeText.text.toString()
            }
        }
        return binding.root
    }
}
