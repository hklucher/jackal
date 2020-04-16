package com.brolo.jackal.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.bottom_navigation.*

class DashboardActivity : AppCompatActivity(R.layout.activity_dashboard) {

    private lateinit var viewModel: GamesViewModel

    companion object {
        @Suppress("unused")
        val TAG = DashboardActivity::class.java.simpleName

        val GAME_LOGGED_OK = 1
    }

    private val loggedGamesObserver = Observer<List<Game>> {
        if (it.isEmpty()) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewPager()
        setupBottomNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GAME_LOGGED_OK && resultCode == Activity.RESULT_OK) {
            // TODO:
            // Store data fetched from API in room
            // Only pull fetch if no data exists in room
            // Insert into room after submitting from API
        }
    }

    // Sets up the ViewPager for top tabs.
    private fun setupViewPager() {
//        val viewPager = dashboard_pager
//        val adapter = DashboardPagerAdapter(supportFragmentManager)
//
//        viewPager.adapter = adapter
//
//        dashboard_tab_layout.setupWithViewPager(viewPager)
//
//        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
//
//        observeGamesViewModel(viewModel)
    }

    private fun setupBottomNavigation() {
//        bottom_app_bar.selectedItemId = R.id.actions_dashboard

//        log_game_fab.setOnClickListener {
//            val intent = Intent(this, NewGameActivity::class.java)
//
//            startActivityForResult(intent, GAME_LOGGED_OK)
//        }
    }

    private fun observeGamesViewModel(gamesViewModel: GamesViewModel) {
        gamesViewModel.allGames.observe(this, loggedGamesObserver)
    }
}
