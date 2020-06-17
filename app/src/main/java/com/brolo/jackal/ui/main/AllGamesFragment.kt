package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.databinding.FragmentAllGamesBinding
import com.brolo.jackal.model.*
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.GamesDiffUtilCallback
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.fragment_all_games.*
import kotlinx.android.synthetic.main.fragment_all_games.new_game_fab

class AllGamesFragment : Fragment(), GameAdapter.OnGameClickListener {
    companion object {
        @Suppress("unused")
        val TAG = AllGamesFragment::class.java.simpleName
    }

    private var _binding: FragmentAllGamesBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: GameAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val gamesViewModel: GamesViewModel by activityViewModels()
    private val mapsViewModel: MapsViewModel by activityViewModels()

    private var gameOptionsSheet: GameOptionsSheet? = null

    private val gameObserver = Observer<List<Game>> { games ->
        mapsViewModel.allMaps.value?.let { maps ->
            if (this::viewAdapter.isInitialized) {
                val oldList = viewAdapter.games
                val diffResult = DiffUtil.calculateDiff(GamesDiffUtilCallback(oldList, games))
                viewAdapter.games = games
                diffResult.dispatchUpdatesTo(viewAdapter)
            } else {
                setupRecyclerView(games, maps)
            }
        }
    }

    private val mapsObserver = Observer<List<Map>> { maps ->
        gamesViewModel.allGames.value?.let { games ->
            setupRecyclerView(games, maps)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllGamesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesViewModel.allGames.observe(viewLifecycleOwner, gameObserver)
        mapsViewModel.allMaps.observe(viewLifecycleOwner, mapsObserver)

        setupButtons()
    }

    override fun onGameClick(position: Int) {
        gamesViewModel.allGames.value?.let { games ->
            val game = games[position]

            gameOptionsSheet = GameOptionsSheet(game)
            gameOptionsSheet?.show(childFragmentManager, "game_options")
        }
    }

    private fun setupRecyclerView(games: List<Game>, maps: List<Map>) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = GameAdapter(games, maps, this)

        recyclerView = all_games_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        initScrollListeners()
    }

    private fun setupButtons() {
        new_game_fab.setOnClickListener {
            NewGameBottomSheet().show(parentFragmentManager, "new_game_dialog")
        }
    }

    private fun initScrollListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) new_game_fab.hide() else new_game_fab.show()
            }
        })
    }
}
