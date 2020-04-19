package com.brolo.jackal.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.brolo.jackal.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class AuthenticatedActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticated)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController

        setupNavToolbar(navController)
        setupBottomNav(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    private fun setupNavToolbar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dashboard_dest, R.id.all_games_dest, R.id.settings_dest)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_app_bar)

        bottomNav?.setupWithNavController(navController)
    }
}
