package com.example.coursework.screens.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursework.databinding.FragmentPlanningBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.example.coursework.screens.adding.AddTrainingAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class PlanTraining : Fragment() {

    private lateinit var binding: FragmentPlanningBinding
    private lateinit var adapter: PlanTrainingAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanningBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val userName = FirebaseAuth.getInstance().currentUser?.displayName
        val trainings =
            FirebaseDatabase.getInstance().getReference("trainings")
        val options = FirebaseRecyclerOptions.Builder<OwnTrainInfo>().setLifecycleOwner(this)
            .setQuery(trainings, OwnTrainInfo::class.java)
            .build()
        adapter = PlanTrainingAdapter(options, object : PlanTrainingAdapter.OnItemClickListener {
            override fun onItemClick(ownTrain: OwnTrainInfo) {
                //
            }
        })
        binding.trainRwPlan.adapter = adapter
        binding.trainRwPlan.layoutManager = LinearLayoutManager(activity)
        binding.trainRwPlan.addItemDecoration(
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