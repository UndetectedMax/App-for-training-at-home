package com.example.coursework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.coursework.databinding.AuthentificatiohnActivityBinding
import com.example.coursework.screens.login.onAuthStateListener

class AuthenticationActivity : AppCompatActivity(), onAuthStateListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var activityBinding: AuthentificatiohnActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = AuthentificatiohnActivityBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.login_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onAuthStateChanged() {
        navController.navigate(
            R.id.action_loginFragment_to_mainActivity
        )
    }
}