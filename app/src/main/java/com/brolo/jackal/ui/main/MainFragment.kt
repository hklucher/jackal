package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.mdoel.Game
import com.brolo.jackal.viewmodel.GamesViewModel
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: GamesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        observeGamesViewModel(viewModel)
        setupLogGameClick()
    }

    private fun observeGamesViewModel(viewModel: GamesViewModel) {
        val gameObserver = Observer<List<Game>> {
            message.text = resources.getQuantityString(R.plurals.total_games, it.size, it.size)
        }

        viewModel.allGames.observe(this, gameObserver)

//        viewModel.getGamesListObservable().observe(this, gameObserver)
    }

    private fun setupLogGameClick() {
        logGame.setOnClickListener {
            val game = Game(0, "attack", null)
            viewModel.insert(game)

//            viewModel.addGame(game)
        }
    }
}
