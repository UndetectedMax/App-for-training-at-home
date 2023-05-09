package com.example.coursework.screens.adding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursework.R
import com.example.coursework.databinding.FragmentAddtrainingBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class AddTraining : Fragment() {
    private lateinit var binding: FragmentAddtrainingBinding
    private lateinit var adapter: AddTrainingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddtrainingBinding.inflate(layoutInflater, container, false)
        //val appUser = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        initRecyclerView()
        binding.addTrainByCode.setOnClickListener {
            val args = Bundle()
            findNavController().navigate(
                R.id.action_add_training_icon_to_addTrainingByCode, args
            )
        }
        binding.addOwnTrainingButton.setOnClickListener {
            val args = Bundle()
            findNavController().navigate(
                R.id.action_add_training_icon_to_addOwnTraining, args
            )
        }
        return binding.root
    }

    private fun initRecyclerView() {
        val trainings = FirebaseDatabase.getInstance().getReference("trainings")
        val options = FirebaseRecyclerOptions.Builder<OwnTrainInfo>().setLifecycleOwner(this)
            .setQuery(trainings, OwnTrainInfo::class.java)
            .build()
        adapter = AddTrainingAdapter(options)
        binding.trainRwAdd.adapter = adapter
        binding.trainRwAdd.layoutManager = LinearLayoutManager(activity)
        binding.trainRwAdd.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}