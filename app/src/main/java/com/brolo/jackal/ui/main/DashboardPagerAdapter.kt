package com.brolo.jackal.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

// Pager Adapter for Dashboard tabs.
class DashboardPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
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
