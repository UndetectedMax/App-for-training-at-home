package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coursework.databinding.AddedTrainDetailsEditBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.example.coursework.repositories.OwnTrain.TrainRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddedTrainDetailsEdit : Fragment() {
    private lateinit var binding: AddedTrainDetailsEditBinding
    private var trainAuthor = ""

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddedTrainDetailsEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val trainCode = arguments?.getString("trainCode")
        val trainReference = FirebaseDatabase.getInstance().getReference("trainings")
            .orderByChild("trainCode").equalTo(trainCode)
        trainReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val train = childSnapshot.getValue(OwnTrainInfo::class.java)
                    train?.let {
                        binding.inputTrainName.setText(it.trainName)
                        binding.inputTrainDesc.setText(it.trainDescription)
                        trainAuthor = it.trainAuthor
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
        binding.setChanges.setOnClickListener {
            if (binding.inputTrainName.text.toString()
                    .isBlank() || binding.inputTrainDesc.text.toString().isBlank()
            ) {
                Toast.makeText(
                    requireContext(),
                    "You need to enter text in the required fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val train = getTrainInfo(trainCode.toString())
                // Обновление тренировки в Firebase
                TrainRepository().createOrUpdateTrain(train)
            }
        }

        binding.deleteTrain.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("You have pressed the delete button")
                .setMessage("Are you sure you want to delete this train?")
                .setNegativeButton("No, I don't want", null)
                .setPositiveButton("Yes, I am really sure") { _, _ ->
                    TrainRepository().deleteTrain(getTrainInfo(trainCode.toString()))
                    findNavController().popBackStack()
                }
                .show()
        }
    }

    private fun getTrainInfo(trainCode: String): OwnTrainInfo {
        val trainName = binding.inputTrainName.text.toString()
        val trainDesc = binding.inputTrainDesc.text.toString()
        return OwnTrainInfo(trainCode, trainAuthor, trainName, trainDesc)
    }
}