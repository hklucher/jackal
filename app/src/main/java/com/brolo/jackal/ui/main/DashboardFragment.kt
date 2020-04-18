package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

// BIG TODOs:
// 1. Figure out how to properly share live data from this fragment to each fragment inside the tab/pager.
// 2. Make sure we're not fetching anymore after we initially populate the data.
// 3. It looks like this data gets re-populated every time the bottom tab changes/go to diff screen.
//    See if there's a way to persist view model data between navigation or persist

class DashboardFragment : Fragment() {
    companion object {
        @Suppress("unused")
        val TAG = DashboardFragment::class.java.simpleName
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

        setupViewPager()
        setupButtons()
    }

    private fun setupViewPager() {
        val viewpager = dashboard_pager

        val adapter = DashboardPagerAdapter(childFragmentManager)
        viewpager.adapter = adapter
        dashboard_tab_layout.setupWithViewPager(viewpager)
    }

    private fun setupButtons() {
        new_game_fab.setOnClickListener {
            findNavController().navigate(R.id.action_dashboard_to_new_game)
        }
    }
}
