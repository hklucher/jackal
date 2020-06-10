package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

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

    companion object {
        @Suppress("unused")
        val TAG = DashboardFragment::class.java.simpleName
    }

}
