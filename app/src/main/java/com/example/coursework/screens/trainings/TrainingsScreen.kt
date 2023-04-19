package com.example.coursework.screens.trainings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.R
import com.example.coursework.databinding.FragmentTrainingsScreenBinding

class TrainingsScreen : Fragment() {
    private lateinit var binding: FragmentTrainingsScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainingsScreenBinding.inflate(layoutInflater, container, false)
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = RVAdaptor(fetchData(), this.requireContext()) {
            val args = Bundle()
            args.putString("text", it)
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(R.id.action_train_icon_to_trainDetails, args)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchData(): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0 until 30)
            list.add("Exercise $i")
        return list
    }


}

