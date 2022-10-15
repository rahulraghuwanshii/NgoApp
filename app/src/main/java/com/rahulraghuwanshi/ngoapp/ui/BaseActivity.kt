package com.rahulraghuwanshi.ngoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.rahulraghuwanshi.ngoapp.R
import com.rahulraghuwanshi.ngoapp.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpNav()
    }

    private fun setUpNav() {
        binding.apply {
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavBar.apply {
                setupWithNavController(navController)
            }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.loginFragment || destination.id == R.id.signUpFragment) {
                    bottomNavBar.visibility = View.GONE
                } else {
                    bottomNavBar.visibility = View.VISIBLE
                }
            }
        }
    }

}