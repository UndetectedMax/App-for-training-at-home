package com.example.coursework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.coursework.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId) {
                   R.id.train_icon -> switchFragment("Train icon")
                   R.id.add_training -> switchFragment("Add training icon")
                   R.id.statistic -> switchFragment("Statistic icon")
                   R.id.settings_icon -> switchFragment("Settings icon")
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.train_icon
    }

    private fun switchFragment(icon:String) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, FirstFragment.newInstance(icon))
        }
    }
}




