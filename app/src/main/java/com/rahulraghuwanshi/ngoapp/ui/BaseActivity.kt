package com.rahulraghuwanshi.ngoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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
        }
    }
}