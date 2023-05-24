package com.example.coursework.screens.adding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coursework.databinding.AddedTrainDetailsBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AddedTrainDetails : Fragment() {
    private lateinit var binding: AddedTrainDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddedTrainDetailsBinding.inflate(layoutInflater, container, false)
        val trainCode = arguments?.getString("trainCode")
        val trainReference = FirebaseDatabase.getInstance().getReference("trainings").orderByChild("trainCode").equalTo(trainCode)
        trainReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val train = childSnapshot.getValue(OwnTrainInfo::class.java)
                    train?.let {
                        binding.addedTrainName.text = it.trainName
                        binding.addedTrainDesc.text = it.trainDescription
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
            }
        })

        /*binding.setDate.setOnClickListener {

        }*/
        return binding.root
    }

}