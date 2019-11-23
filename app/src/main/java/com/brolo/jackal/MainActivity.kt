package com.brolo.jackal

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.model.FilterOption
import com.brolo.jackal.model.Game
import com.brolo.jackal.ui.main.LogGameDialogFragment
import com.brolo.jackal.ui.main.PieChartFragment
import com.brolo.jackal.viewmodel.FilterOptionsViewModel
import com.brolo.jackal.viewmodel.GamesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(R.layout.main_activity),
    LogGameDialogFragment.LogGameDialogFragmentListener {

    private lateinit var viewModel: GamesViewModel
    private lateinit var filterOptionsViewModel: FilterOptionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        filterOptionsViewModel = ViewModelProviders.of(this).get(FilterOptionsViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.chart_fragment_container, PieChartFragment.newInstance())
            .commit()

        observeGamesViewModel(viewModel)
        observeFilterOptionsViewModel(filterOptionsViewModel)

        setupLogGameClick()
        setupFilterChips()
    }

    override fun onGameCreated(game: Game) {
        insertGame(game)
    }

    private fun observeGamesViewModel(viewModel: GamesViewModel) {
        val gameObserver = Observer<List<Game>> {
            message.text = resources.getQuantityString(R.plurals.total_games, it.size, it.size)
        }

        viewModel.allGames.observe(this, gameObserver)
    }

    private fun observeFilterOptionsViewModel(viewModel: FilterOptionsViewModel) {
        val filterObserver = Observer<List<FilterOption>> {
            Log.d("MainActivity", "Changed")
        }

        viewModel.allFilterOptions.observe(this, filterObserver)
    }

    private fun setupLogGameClick() {
        logGame.setOnClickListener {
            LogGameDialogFragment().show(supportFragmentManager, "log_game_dialog")
        }
    }

    private fun insertGame(game: Game) {
        viewModel.insert(game)

        Snackbar.make(main, R.string.game_logged, Snackbar.LENGTH_LONG).show()
    }

    private fun setupFilterChips() {
//        chip_start_side.chipBackgroundColor = ContextCompat.getColor(this, R.color.colorAccent)
    }
}
