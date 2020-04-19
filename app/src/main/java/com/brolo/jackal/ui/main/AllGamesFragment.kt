package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.*
import com.brolo.jackal.model.Map
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.fragment_all_games.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllGamesFragment :
    Fragment(),
    GameAdapter.OnGameClickListener,
    GameOptionsSheet.OnOptionSelectedListener {

    companion object {
        @Suppress("unused")
        val TAG = AllGamesFragment::class.java.simpleName
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var gamesViewModel: GamesViewModel
    private lateinit var mapsViewModel: MapsViewModel

    private val apiInstance = ApiInstance.getInstance().create(ApiDataService::class.java)

    private var gameOptionsSheet: GameOptionsSheet? = null

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

    override fun onOptionSelected(option: GameOptionType, game: Game) {
        when (option) {
            GameOptionType.MARK_GAME_WON -> updateGameStatus(game, Game.StatusWon)
            GameOptionType.MARK_GAME_LOST -> updateGameStatus(game, Game.StatusLost)
            GameOptionType.DELETE_GAME -> deleteGame(game)
        }

        showToastMessage(option)
        gameOptionsSheet?.dismiss()
    }

    override fun onGameClick(position: Int) {
        gamesViewModel.allGames.value?.let { games ->
            val game = games[position]

            gameOptionsSheet = GameOptionsSheet(this, game)
            gameOptionsSheet?.show(childFragmentManager, "game_options")
        }
    }

    private fun updateGameStatus(game: Game, status: String) {
        game.status = status

        val gameRequest = GameRequest(game)
        val request = apiInstance.updateGame(game.id, gameRequest)

        request.enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {}
            override fun onFailure(call: Call<GameResponse>, t: Throwable) {}
        })

        // FIXME: When updating game status, recyclerview scrolls to top
        GlobalScope.launch {
            gamesViewModel.update(game)
        }
    }

    private fun deleteGame(game: Game) {
        val request = apiInstance.deleteGame(game.id)

        request.enqueue(object : Callback<Response<Void>> {
            override fun onResponse(
                call: Call<Response<Void>>,
                response: Response<Response<Void>>
            ) {}

            override fun onFailure(call: Call<Response<Void>>, t: Throwable) {}
        })

        GlobalScope.launch { gamesViewModel.delete(game) }
    }

    private fun showToastMessage(optionType: GameOptionType) {
        val message = when (optionType) {
            GameOptionType.MARK_GAME_WON -> "Game marked as won, congrats!"
            GameOptionType.MARK_GAME_LOST -> "Game marked as lost, better luck next time!"
            GameOptionType.DELETE_GAME -> "Game deleted."
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
