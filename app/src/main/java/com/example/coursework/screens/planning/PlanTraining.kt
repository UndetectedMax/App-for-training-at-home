package com.example.coursework.screens.planning

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursework.R
import com.example.coursework.databinding.FragmentPlanningBinding
import com.example.coursework.repositories.OwnTrain.OwnTrainInfo
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class PlanTraining : Fragment() {

    private lateinit var binding: FragmentPlanningBinding
    private lateinit var adapter: PlanTrainingAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanningBinding.inflate(inflater, container, false)
        initRecyclerView()
        setHasOptionsMenu(true)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchTrainings(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchTrainings(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun initRecyclerView() {
        val trainings = FirebaseDatabase.getInstance().getReference("trainings")
        val options = FirebaseRecyclerOptions.Builder<OwnTrainInfo>()
            .setQuery(trainings, OwnTrainInfo::class.java)
            .build()
        adapter = PlanTrainingAdapter(options, object : PlanTrainingAdapter.OnItemClickListener {
            override fun onItemClick(ownTrain: OwnTrainInfo) {
                // Обработка щелчка на элементе списка
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

    private fun searchTrainings(query: String) {
        val trainingsRef = FirebaseDatabase.getInstance().getReference("trainings")
        val queryRef = trainingsRef.orderByChild("trainName").startAt(query).endAt(query + "\uf8ff")
        val options = FirebaseRecyclerOptions.Builder<OwnTrainInfo>()
            .setQuery(queryRef, OwnTrainInfo::class.java)
            .build()
        adapter.updateOptions(options)
        adapter.startListening()
        binding.trainRwPlan.adapter = adapter
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
