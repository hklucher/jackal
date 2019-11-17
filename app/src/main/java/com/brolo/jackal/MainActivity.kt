package com.brolo.jackal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.mdoel.Game
import com.brolo.jackal.ui.main.LogGameDialogFragment
import com.brolo.jackal.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.main_activity.logGame
import kotlinx.android.synthetic.main.main_activity.message

class MainActivity : AppCompatActivity(R.layout.main_activity),
    LogGameDialogFragment.LogGameDialogFragmentListener {

    private lateinit var viewModel: GamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        observeGamesViewModel(viewModel)

        setupLogGameClick()
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

    private fun setupLogGameClick() {
        logGame.setOnClickListener {
            LogGameDialogFragment().show(supportFragmentManager, "log_game_dialog")
        }
    }

    private fun insertGame(game: Game) {
        viewModel.insert(game)
    }
}
