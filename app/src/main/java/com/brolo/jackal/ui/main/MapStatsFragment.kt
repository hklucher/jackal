package com.brolo.jackal.ui.main

import android.graphics.Color
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
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_map_stats.*

class MapStatsFragment : Fragment() {

    private lateinit var gamesViewModel: GamesViewModel
    private lateinit var mapsViewModel: MapsViewModel

    private val gameObserver = Observer<List<Game>> { setupChart() }

    private val mapsObserver= Observer<List<Map>> { setupChart() }

//    private val allMapsColors = listOf(
//    )

    companion object {
        fun newInstance(): MapStatsFragment {
            return MapStatsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGamesViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapsViewModel.allMaps.removeObserver(mapsObserver)
        gamesViewModel.allGames.removeObserver(gameObserver)
    }

    private fun observeGamesViewModel() {
        activity?.let {
            gamesViewModel = ViewModelProviders.of(it).get(GamesViewModel::class.java)
            mapsViewModel = ViewModelProviders.of(it).get(MapsViewModel::class.java)

            gamesViewModel.allGames.observe(it, gameObserver)
            mapsViewModel.allMaps.observe(it, mapsObserver)
        }
    }

    private fun setupChart() {
        val entries = arrayListOf<BarEntry>()

        mapsViewModel.allMaps.value?.forEachIndexed { i, map ->
            val gameCount = gamesViewModel.allGames.value?.count { it.mapId == map.id }

            if (gameCount != null) {
                entries.add(BarEntry(i.toFloat(), gameCount.toFloat()))
            }
        }

        val currentContext = context

        val set = BarDataSet(entries, "BarDataSet")

        // TODO: Better colors
        if (currentContext != null) {
            set.colors = arrayListOf(
                ContextCompat.getColor(currentContext, R.color.md_red_A700),
                ContextCompat.getColor(currentContext, R.color.md_purple_800),
                ContextCompat.getColor(currentContext, R.color.md_indigo_A700),
                ContextCompat.getColor(currentContext, R.color.md_green_A700),
                ContextCompat.getColor(currentContext, R.color.md_amber_900)
            )
        }

        val data = BarData(set)
        data.barWidth = 0.9f
        maps_bar_chart.data = data
        maps_bar_chart.setFitBars(true)
        maps_bar_chart.invalidate()
    }
}
