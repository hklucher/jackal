package com.brolo.jackal.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.activity_dashboard.*

// Pager Adapter for Dashboard tabs.
class DashboardPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Win / Loss"
            1 -> "Starting Teams"
            2 -> "Maps"
            else -> null
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> WinLossStatsFragment.getInstance()
            1 -> TeamStatsFragment.getInstance()
            2 -> MapsPlayedStatsFragment.getInstance()
            // Default to MapsPlayedStatsFragment if we get an index we don't recognize
            else -> MapsPlayedStatsFragment.getInstance()
        }
    }
}

class DashboardActivity : AppCompatActivity(R.layout.activity_dashboard) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewPager = dashboard_pager
        val adapter = DashboardPagerAdapter(supportFragmentManager)

        viewPager.adapter = adapter

        dashboard_tab_layout.setupWithViewPager(viewPager)
    }
}