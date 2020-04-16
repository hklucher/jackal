package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    companion object {
        @Suppress("unused")
        val TAG = DashboardFragment::class.java.simpleName
    }

    private lateinit var gamesViewModel: GamesViewModel

    private val loggedGamesObserver = Observer<List<Game>> {
        Log.d(TAG, "loggedGamesChanged")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
   ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        setupViewPager()
        setupGamesViewModel()
    }

    private fun setupViewPager() {
        val viewpager = dashboard_pager

        val adapter = DashboardPagerAdapter(parentFragmentManager)
        viewpager.adapter = adapter
        dashboard_tab_layout.setupWithViewPager(viewpager)
    }

    private fun setupGamesViewModel() {
        gamesViewModel.allGames.observe(viewLifecycleOwner, loggedGamesObserver)
    }
}
