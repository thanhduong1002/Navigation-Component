package com.example.navigationcomponent

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var isBottomNavVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment

        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.cartFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.cartFragment)
                }

                R.id.productFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.productFragment)
                }

                R.id.toDoFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.toDoFragment)
                }

                R.id.userFragment -> {
                    if (!isBottomNavVisible) {
                        isBottomNavVisible = true
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    navController.navigate(R.id.userFragment)
                }

                R.id.profileFragment -> {
                    isBottomNavVisible = false
                    bottomNavigationView.visibility = View.GONE
                    navController.navigate(R.id.profileFragment)
                }
            }
        }

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#70A7D8")))
    }
}