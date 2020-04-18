package com.brolo.jackal.ui.main

import android.content.Context
import android.media.midi.MidiOutputPort
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.fragment_all_games.*

class AllGamesFragment : Fragment(), GameAdapter.OnGameClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var gamesViewModel: GamesViewModel
    private lateinit var mapsViewModel: MapsViewModel

    private val gameObserver = Observer<List<Game>> { games ->
        val maps = mapsViewModel.allMaps.value

        if (maps != null) {
            setupRecyclerView(games, maps)
        }
    }

    private val mapsObserver = Observer<List<Map>> { maps ->
        val games = gamesViewModel.allGames.value

        if (games != null) {
            setupRecyclerView(games, maps)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        gamesViewModel.allGames.observe(viewLifecycleOwner, gameObserver)

        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        mapsViewModel.allMaps.observe(viewLifecycleOwner, mapsObserver)
    }

    override fun onGameClick(position: Int) {
        GameOptionsSheet().show(childFragmentManager, "game_options")
    }

    private fun setupRecyclerView(games: List<Game>, maps: List<Map>) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = GameAdapter(games, maps, this)

        recyclerView = all_games_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
