package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brolo.jackal.R

class MapsPlayedStatsFragment : Fragment() {
    companion object {
        fun getInstance(): MapsPlayedStatsFragment {
            return MapsPlayedStatsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps_played_stats, container, false)
    }
}
