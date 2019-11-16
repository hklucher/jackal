package com.brolo.jackal.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        observeGamesViewModel(viewModel)
    }

    private fun observeGamesViewModel(viewModel: GamesViewModel) {
        val gameObserver = Observer<List<Game>> {
            message.text = getString(R.string.total_games, it.size)
        }

        viewModel.getGamesListObservable().observe(this, gameObserver)
    }

}
