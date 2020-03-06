package com.brolo.jackal.ui.main

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
    }

    private val loggedGamesObserver = Observer<List<Game>> {
        Log.d(TAG, "loggedGamesObserver updated!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewPager()
        setupBottomNavigation()
    }

    // Sets up the ViewPager for top tabs.
    private fun setupViewPager() {
        val viewPager = dashboard_pager
        val adapter = DashboardPagerAdapter(supportFragmentManager)

        viewPager.adapter = adapter

        dashboard_tab_layout.setupWithViewPager(viewPager)

        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        observeGamesViewModel(viewModel)
    }

    private fun setupBottomNavigation() {
        bottom_app_bar.selectedItemId = R.id.actions_dashboard

        log_game_fab.setOnClickListener {
            val intent = Intent(this, NewGameActivity::class.java)

            startActivity(intent)
        }
    }

    private fun observeGamesViewModel(gamesViewModel: GamesViewModel) {
        gamesViewModel.allGames.observe(this, loggedGamesObserver)
    }
}
