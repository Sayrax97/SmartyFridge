package com.team4infinity.smartyfridge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team4infinity.smartyfridge.R
import dagger.hilt.android.AndroidEntryPoint
import com.team4infinity.smartyfridge.utils.TAG

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var bottomNavBar:BottomNavigationView
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init() {
        bottomNavBar = findViewById(R.id.bottom_nav_main)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavBar.setupWithNavController(navController)
    }
}