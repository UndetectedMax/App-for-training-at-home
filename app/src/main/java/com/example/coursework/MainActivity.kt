package com.example.coursework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.coursework.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.train_icon -> supportFragmentManager.commit { replace(R.id.first_container, FirstFragment()) }
                R.id.add_training -> supportFragmentManager.commit { replace(R.id.second_container, SecondFragment()) }
                R.id.statistic -> supportFragmentManager.commit { replace(R.id.third_container, ThirdFragment()) }
                R.id.settings_icon -> supportFragmentManager.commit { replace(R.id.fourth_container, FourthFragment()) }
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.train_icon
    }
}