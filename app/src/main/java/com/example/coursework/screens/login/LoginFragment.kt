package com.example.coursework.screens.login

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coursework.R
import com.example.coursework.repositories.User.UserRepository
import com.example.coursework.repositories.User.mapFromFirebaseUser
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onAuthStateListener) {
            userListener = context
        }
    }

    private fun onSignInResult(res: FirebaseAuthUIAuthenticationResult) {
        if (res.resultCode == AppCompatActivity.RESULT_OK) {
            FirebaseAuth.getInstance().currentUser?.also {
                UserRepository().createOrUpdateUser(mapFromFirebaseUser(it))
                userListener?.onAuthStateChanged()
            } ?: Toast.makeText(this.requireContext(), "Login failed, try again", Toast.LENGTH_LONG)
                .show()
        } else {
            if (res.idpResponse == null) {
                requireActivity().finish()
            } else {
                Toast.makeText(this.requireContext(), "Login failed, try again", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun doLogin() {
        val signIn = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.icon_app)
            .setIsSmartLockEnabled(false)
            .build()
        launcher.launch(signIn)
    }
}
