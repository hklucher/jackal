package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.model.MapStatsItemType
import com.brolo.jackal.model.MapStatsListItem
import com.brolo.jackal.utils.BarChartXAxisFormatter
import com.brolo.jackal.utils.BarChartYAxisFormatter
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val gamesObserver = Observer<List<Game>> { games ->
        val maps = mapsViewModel.allMaps.value

        whenNotNull(maps) { initRecyclerView(games, it) }
    }

    private val mapsObserver = Observer<List<Map>> { maps ->
        val games = gamesViewModel.allGames.value

        whenNotNull(games) { initRecyclerView(it, maps) }
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

    private fun initRecyclerView(games: List<Game>, maps: List<Map>) {
        val currentContext = context

        if (currentContext != null) {
            viewManager = LinearLayoutManager(currentContext)
            viewAdapter = MapsPlayedAdapter(getListItems(games, maps), games, maps, currentContext)

            recyclerView  = maps_played_stats_recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    private fun getListItems(games: List<Game>, maps: List<Map>): List<MapStatsListItem> {
        val items = mutableListOf<MapStatsListItem>()

        items.add(MapStatsListItem(MapStatsItemType.Chart))

        maps.forEach {
            val wonCount = games.fold(0) { sum, game ->
                if (game.didWin() && game.mapId == it.id) {
                    sum + 1
                } else {
                    sum
                }
            }

            val lostCount = games.fold(0) { sum, game ->
                if (game.didLose() && game.mapId == it.id) {
                    sum + 1
                } else {
                    sum
                }
            }

            items.add(MapStatsListItem(MapStatsItemType.MapOverview, it, wonCount, lostCount))
        }

        return items
    }

    companion object {
        fun getInstance(): MapsPlayedStatsFragment {
            return MapsPlayedStatsFragment()
        }
    }

}
