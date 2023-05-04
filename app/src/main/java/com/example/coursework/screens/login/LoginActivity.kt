package com.example.coursework.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.coursework.MainActivity

import com.example.coursework.repositories.UserRepository
import com.example.coursework.repositories.mapFromFirebaseUser
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity:AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            doLogin()
        }

        private val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        private val launcher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            this.onSignInResult(res)
        }

        private var userListener: onAuthStateListener? = null

        fun onAttach(context: Context) {
            if (context is onAuthStateListener) {
                userListener = context
            }
        }

        private fun onSignInResult(res: FirebaseAuthUIAuthenticationResult) {
            if (res.resultCode == AppCompatActivity.RESULT_OK) {
                FirebaseAuth.getInstance().currentUser?.also {
                    UserRepository().createOrUpdateUser(mapFromFirebaseUser(it))
                    userListener?.onAuthStateChanged()

                } ?: Toast.makeText(this, "Login failed, try again", Toast.LENGTH_LONG)
                    .show()
            } else {
                if (res.idpResponse == null) {
                    finish()
                } else {
                    Toast.makeText(this, "Login failed, try again", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        private fun doLogin() {
            val signIn = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls(
                    "https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwidjanP4Nn-AhXv-yoKHersBvMQwqsBegQIDhAF&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DdQw4w9WgXcQ&usg=AOvVaw0aHtehaphMhOCAkCydRLZU",
                    "https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwjIp8bf4Nn-AhXKmYsKHZpmBjkQFnoECA0QAQ&url=https%3A%2F%2Fel.opu.ua%2F&usg=AOvVaw0ItS1CnIokiTPrQwCY1yJ-"
                ).build()
            launcher.launch(signIn)
        }
    }
