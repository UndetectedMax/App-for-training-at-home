package com.example.coursework.screens.adding

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.coursework.R
import com.example.coursework.databinding.FragmentAddTrainingByCodeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("SetTextI18n")
class AddTrainingByCode : Fragment(R.layout.fragment_add_training_by_code) {
    private lateinit var binding: FragmentAddTrainingByCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTrainingByCodeBinding.inflate(layoutInflater, container, false)
        binding.submitButton.setOnClickListener {
            if (binding.enterCodeText.text.isBlank()) {
                Toast.makeText(
                    requireContext(),
                    "You need to enter code in the required field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val trainCode = binding.enterCodeText.text.toString()
                val query = FirebaseDatabase.getInstance().getReference("trainings")
                    .orderByChild("trainCode")
                    .equalTo(trainCode)

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val args = Bundle()
                            args.putString("trainCode", trainCode)
                            findNavController().navigate(
                                R.id.action_addTrainingByCode_to_addedTrainDetails, args
                            )
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "This train does not exist!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        }
        return binding.root
    }
}
