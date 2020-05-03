package com.brolo.jackal.ui.main

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
import com.brolo.jackal.utils.GameAdapterOpts
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.fragment_latest_games.*

class LatestGamesFragment : Fragment(), GameAdapter.OnGameClickListener {

    private var lastThreeGames = listOf<Game>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var gamesViewModel: GamesViewModel
    private val gamesObserver = Observer<List<Game>> {
        lastThreeGames = it.take(3)

        val maps = mapsViewModel.allMaps.value

        if (maps != null) {
            populateRecyclerView(lastThreeGames, maps)
        }
    }

    private lateinit var mapsViewModel: MapsViewModel
    private val mapsObserver = Observer<List<com.brolo.jackal.model.Map>> {
        populateRecyclerView(lastThreeGames, it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_latest_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        gamesViewModel.allGames.observe(viewLifecycleOwner, gamesObserver)

        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        mapsViewModel.allMaps.observe(viewLifecycleOwner, mapsObserver)
    }

    override fun onGameClick(position: Int) {
    }

    override fun onGameLongPress(position: Int) {
    }

    private fun populateRecyclerView(games: List<Game>, maps: List<com.brolo.jackal.model.Map>) {
        val gameAdapterOpts = GameAdapterOpts(GameAdapter.ViewMode.Card, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = GameAdapter(games, maps, this, context, gameAdapterOpts)
        recyclerView = latest_games_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        fun createInstance(): LatestGamesFragment {
            return LatestGamesFragment()
        }
    }

}
