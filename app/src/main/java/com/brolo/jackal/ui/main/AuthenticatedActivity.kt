package com.brolo.jackal.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.brolo.jackal.R
import com.brolo.jackal.utils.AuthUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class AuthenticatedActivity  : AppCompatActivity() {

    private val logoutReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val loginIntent = Intent(context, LoginActivity::class.java)

            deleteAuthData()
            startActivity(loginIntent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticated)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = host.navController

        setupNavToolbar(navController)
        setupBottomNav(navController)
        registerLogoutReceiver()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(logoutReceiver)
    }

    private fun setupNavToolbar(navController: NavController) {
        supportActionBar?.elevation = 0f

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dashboard_dest, R.id.all_games_dest, R.id.settings_dest)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_app_bar)

        bottomNav?.setupWithNavController(navController)
    }

    private fun registerLogoutReceiver() {
        registerReceiver(logoutReceiver, IntentFilter("${packageName}.ACTION_LOGOUT"))
    }

    private fun deleteAuthData() {
        AuthUtils.deleteJWT(this)
        AuthUtils.deleteUserId(this)
    }

    companion object {
        val TAG = AuthenticatedActivity::class.java.simpleName
    }

}
