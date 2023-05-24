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
import com.example.coursework.repositories.OwnTrain.TrainRepository
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random

class AddOwnTraining : Fragment() {
    private lateinit var binding: FragmentAddOwnTrainingBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val repository = TrainRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddOwnTrainingBinding.inflate(layoutInflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.ownTrainSubmit.setOnClickListener {
            repository.createOrUpdateTrain(getInfoForTraining())
            findNavController().navigate(R.id.action_addOwnTraining_to_add_training_icon)
        }
        return binding.root
    }
    private fun getInfoForTraining(): OwnTrainInfo {
        val trainCode = Random.nextInt(1000000000).toString()
        val author = firebaseAuth.currentUser?.email.toString()
        val trainName = binding.ownTrainName.editText?.text.toString()
        val trainDescription = binding.ownTrainDescription.editText?.text.toString()
        return OwnTrainInfo(trainCode, author, trainName, trainDescription)
    }
}