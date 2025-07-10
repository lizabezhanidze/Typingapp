package com.example.typingapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var navMenu: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navMenu = findViewById(R.id.bottomNav)
        initScores()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        showAuthFlow()
//        onLoginSuccess()
    }

    private fun showAuthFlow() {
        navMenu.visibility = View.GONE
        navController.navigate(R.id.authFragment)
    }

    fun onLoginSuccess() {
        setupMainNavigation()
        navController.navigate(R.id.mainFragment)
    }

    private fun setupMainNavigation() {
        navMenu.visibility = View.VISIBLE
        val appBarConfig = AppBarConfiguration(
            setOf(R.id.mainFragment, R.id.scrollFragment, R.id.profileFragment, R.id.settingsFragment)
        )
        setupActionBarWithNavController(navController, appBarConfig)
        navMenu.setupWithNavController(navController)
    }

    private fun initScores() {
        val wpms = arrayListOf<Int>()
        for (i in 1..15) {
            wpms.add(Random.nextInt(50, 100))
        }
        getSharedPreferences("Settings", 0).edit {
            putString("Scores", wpms.joinToString(","))
            apply()
        }
    }
}
