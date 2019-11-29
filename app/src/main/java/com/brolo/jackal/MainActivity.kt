package com.brolo.jackal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.ui.main.GameAdapter
import com.brolo.jackal.ui.main.LogGameDialogFragment
import com.brolo.jackal.ui.main.MapStatsFragment
import com.brolo.jackal.ui.main.PieChartFragment
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(R.layout.main_activity),
    LogGameDialogFragment.LogGameDialogFragmentListener {

    private lateinit var viewModel: GamesViewModel
    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.chart_fragment_container, PieChartFragment.newInstance())
            .commit()

        observeGamesViewModel(viewModel)

        setupLogGameClick()
        setupFilterChips()
    }

    override fun onGameCreated(game: Game) {
        insertGame(game)
    }

    private fun observeGamesViewModel(viewModel: GamesViewModel) {
        //  TODO: Move these to outer scope
        // TODO: Rename viewModel to gamesViewModel
        val gameObserver = Observer<List<Game>> {
            message.text = resources.getQuantityString(R.plurals.total_games, it.size, it.size)

            val allMaps = mapsViewModel.allMaps.value

            if (allMaps != null) {
                setupRecyclerView(it, allMaps)
            }
        }

        val mapsObserver = Observer<List<Map>> {
            val allGames = viewModel.allGames.value

            if (allGames != null) {
                setupRecyclerView(allGames, it)
            }
        }

        viewModel.allGames.observe(this, gameObserver)
        mapsViewModel.allMaps.observe(this, mapsObserver)
    }

    private fun setupRecyclerView(games: List<Game>, maps: List<Map>) {
        // TODO: Limit results to 20, don't use reverse, instead ensure database sorts for us
        // when we have createdAt fields
        viewManager = LinearLayoutManager(this)
        viewAdapter = GameAdapter(games.reversed(), maps)

        recyclerView = game_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun setupLogGameClick() {
        logGame.setOnClickListener {
            LogGameDialogFragment().show(supportFragmentManager, "log_game_dialog")
        }
    }

    private fun insertGame(game: Game) {
        viewModel.insert(game)

        showGameAddedSnackbar()
    }

    private fun showGameAddedSnackbar() {
        Snackbar.make(main, R.string.game_logged, Snackbar.LENGTH_LONG).also {
            it.setAction(R.string.undo) {
                val lastGame = viewModel.allGames.value?.last()

                if (lastGame != null) {
                    viewModel.delete(lastGame)
                }
            }

            it.show()
        }
    }

    private fun setupFilterChips() {
        chip_group.setOnCheckedChangeListener { _, checkedId ->
            setChartFragment(checkedId)
        }
    }

    private fun setChartFragment(checkedId: Int) {
        when (checkedId) {
            R.id.chip_start_side ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.chart_fragment_container, PieChartFragment.newInstance())
                    .commit()
            R.id.chip_maps ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.chart_fragment_container, MapStatsFragment.newInstance())
                    .commit()
        }
    }
}
