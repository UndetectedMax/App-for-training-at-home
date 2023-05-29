package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coursework.databinding.AddedTrainDetailsEditBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddedTrainDetailsEdit : Fragment() {
    private lateinit var binding: AddedTrainDetailsEditBinding

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
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}