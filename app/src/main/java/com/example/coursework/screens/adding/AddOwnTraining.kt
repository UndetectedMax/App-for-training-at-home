package com.example.coursework.screens.adding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coursework.R
import com.example.coursework.databinding.FragmentAddOwnTrainingBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddOwnTraining : Fragment() {
    private lateinit var binding: FragmentAddOwnTrainingBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddOwnTrainingBinding.inflate(layoutInflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.ownTrainSubmit.setOnClickListener {

            val trainCode = binding.inputCode.text.toString()
            val author = firebaseAuth.currentUser?.displayName.toString()
            val trainName = binding.ownTrainName.editText?.text.toString()
            val trainDescription = binding.ownTrainDescription.editText?.text.toString()

            val madeTrain = OwnTrainInfo(trainCode,author,trainName,trainDescription)

            FirebaseDatabase.getInstance().getReference("trainings").child(madeTrain.trainCode).setValue(madeTrain)

            findNavController().navigate(R.id.action_addOwnTraining_to_add_training_icon)
        }
        return binding.root
    }
}