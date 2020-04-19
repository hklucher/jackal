package com.brolo.jackal.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.BarChartXAxisFormatter
import com.brolo.jackal.utils.whenNotNull
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_map_stats.*
import kotlinx.android.synthetic.main.fragment_maps_played_stats.*

class MapsPlayedStatsFragment : Fragment() {

    private lateinit var gamesViewModel: GamesViewModel
    private lateinit var mapsViewModel: MapsViewModel

    private val gamesObserver = Observer<List<Game>> { games ->
        val maps = mapsViewModel.allMaps.value

        whenNotNull(maps) { setupBarChart(games, it) }
    }

    private val mapsObserver = Observer<List<Map>> { maps ->
        val games = gamesViewModel.allGames.value

        whenNotNull(games) { setupBarChart(it, maps) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps_played_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)

        gamesViewModel.allGames.observe(viewLifecycleOwner, gamesObserver)
        mapsViewModel.allMaps.observe(viewLifecycleOwner, mapsObserver)
    }

    private fun setupBarChart(games: List<Game>, maps: List<Map>) {
        val entries = arrayListOf<BarEntry>()
        val formatter = BarChartXAxisFormatter(maps)

        maps.forEachIndexed { index, map ->
            val wonGames = games.filter { it.didWin() && it.mapId == map.id }
            val lostGames = games.filter { it.didLose() && it.mapId == map.id }

            // Only add games to bar chart when at least one game has been completed on the map.
            if (wonGames.isNotEmpty() || lostGames.isNotEmpty())  {
                entries.add(BarEntry(
                    index.toFloat(), floatArrayOf(wonGames.size.toFloat(), lostGames.size.toFloat())
                ))
            }
        }

        val set = BarDataSet(entries, "")

        set.stackLabels = arrayOf("Won", "Lost")

        set.colors = listOf(
            ContextCompat.getColor(requireContext(), R.color.colorMaterialOrange),
            ContextCompat.getColor(requireContext(), R.color.colorMaterialBlue)
        )

        val data = BarData(set)

        data.barWidth = 0.5f

        data.setDrawValues(false)

        maps_played_bar_chart.axisLeft.setDrawGridLines(false)
        maps_played_bar_chart.data = data
        maps_played_bar_chart.description.isEnabled = false
        maps_played_bar_chart.xAxis.valueFormatter = formatter
        maps_played_bar_chart.setFitBars(true)
        maps_played_bar_chart.setFitBars(true)
        maps_played_bar_chart.invalidate()
    }

    companion object {
        fun getInstance(): MapsPlayedStatsFragment {
            return MapsPlayedStatsFragment()
        }
    }

}
