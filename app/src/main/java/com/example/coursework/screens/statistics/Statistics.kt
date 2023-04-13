package com.example.coursework.screens.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coursework.R
import com.example.coursework.databinding.FragmentSettingsBinding
import com.example.coursework.databinding.FragmentStatisticsBinding

class Statistics : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatisticsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}