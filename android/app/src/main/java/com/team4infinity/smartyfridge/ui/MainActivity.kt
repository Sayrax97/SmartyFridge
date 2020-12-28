package com.team4infinity.smartyfridge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team4infinity.smartyfridge.R
import com.team4infinity.smartyfridge.firebaseEntities.RecipeDTO
import dagger.hilt.android.AndroidEntryPoint
import com.team4infinity.smartyfridge.utils.TAG

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var bottomNavBar:BottomNavigationView
    lateinit var navController: NavController
    private val viewModel: TestViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init() {
        bottomNavBar = findViewById(R.id.bottom_nav_main)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavBar.setupWithNavController(navController)
        viewModel.getRecipes()
        viewModel.recipe.observe(this,{
            data ->
            println("Recipe: $data")
        })
    }
}