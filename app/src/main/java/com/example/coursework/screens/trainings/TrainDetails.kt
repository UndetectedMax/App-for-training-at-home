package com.example.coursework.screens.trainings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coursework.R
import com.example.coursework.databinding.FragmentStatisticsBinding
import com.example.coursework.databinding.FragmentTrainDetailsBinding


class TrainDetails : Fragment() {

    private lateinit var binding:FragmentTrainDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTrainDetailsBinding.inflate(layoutInflater, container, false)

        val title = arguments?.getString("text",).toString()

        binding.trainName.text = title

        Toast.makeText(context,title,Toast.LENGTH_SHORT).show()

        return binding.root
    }

}