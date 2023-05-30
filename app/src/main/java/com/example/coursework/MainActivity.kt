package com.example.coursework

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.coursework.databinding.ActivityMainBinding
import com.example.coursework.repositories.User.UserInfo
import com.example.coursework.repositories.User.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentUser: UserInfo
    private val userRepository = UserRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, AuthenticationActivity::class.java))
        } else {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            drawerLayout = binding.drawerLayout
            binding.drawer.setNavigationItemSelectedListener { item ->
                onOptionsItemSelected(item)
            }
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.train_icon,
                    R.id.settings_icon,
                    R.id.add_training_icon,
                    R.id.planning_icon
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)

            FirebaseAuth.getInstance().currentUser?.let {
                userRepository.getCurrentUserById(
                    it,
                    object : UserRepository.FetchSportsmanListener {
                        override fun onFetchSportsman(user: UserInfo) {
                            currentUser = user
                            showDetails(currentUser, it)
                        }
                    })
            }

        }
    }

    private fun showDetails(currentUser: UserInfo, it: FirebaseUser) {
        val userAvatar =
            binding.drawer.getHeaderView(0).findViewById<ImageView>(R.id.avatar_drawer_header)
        val userEmail =
            binding.drawer.getHeaderView(0).findViewById<TextView>(R.id.name_drawer_header)
        val userName =
            binding.drawer.getHeaderView(0).findViewById<TextView>(R.id.email_drawer_header)
        userName.text = currentUser.displayName
        userEmail.text = it.email
        currentUser.photo?.let {
            if (it.isNotEmpty()) {
                Picasso.get().load(currentUser.photo.toString()).into(userAvatar)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_icon -> {
                val googleSignInClient =
                    GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
                AlertDialog.Builder(this)
                    .setTitle("You have pressed the logout button")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton("No,I don`t want", null)
                    .setPositiveButton("Yes,I am really sure") { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        googleSignInClient.signOut()
                        startActivity(Intent(this, MainActivity::class.java))
                        this.finish()
                    }
                    .show()

                return true
            }

            else -> {
                drawerLayout.closeDrawer(binding.drawer)
            }
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0)
            finish()
        super.onBackPressed()
    }

}
