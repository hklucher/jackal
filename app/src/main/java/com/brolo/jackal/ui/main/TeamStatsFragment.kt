package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brolo.jackal.databinding.FragmentTeamStatsBinding

class TeamStatsFragment : Fragment() {
    private var _binding: FragmentTeamStatsBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): TeamStatsFragment {
            return TeamStatsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamStatsBinding.inflate(inflater, container, false)

        return binding.root
    }
}
