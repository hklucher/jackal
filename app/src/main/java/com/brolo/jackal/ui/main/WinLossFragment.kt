package com.brolo.jackal.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.utils.CalcUtils
import com.brolo.jackal.viewmodel.GamesViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_win_loss.*

class WinLossFragment : Fragment() {

    private lateinit var gamesViewModel: GamesViewModel

    private val gameObserver = Observer<List<Game>> {
//        setupChart(it)
    }

    companion object {
        fun newInstance(): WinLossFragment {
            return WinLossFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_win_loss, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGamesViewModel()
    }

    private fun observeGamesViewModel() {
        activity?.let {
            gamesViewModel = ViewModelProviders.of(it).get(GamesViewModel::class.java)
            gamesViewModel.allGames.observe(it, gameObserver)
        }
    }

    private fun setupChart(allGames: List<Game>) {
        val entries = mutableListOf<PieEntry>()
        val pieChart = win_loss_pie_chart

        if (pieChart != null) {
            TODO("Re-implement")
//            val wonGames = allGames.filter { game -> game.didWin == true }
//            val lostGames = allGames.filter { game -> game.didWin == false }
//            val wonPercentage = CalcUtils.percentage(wonGames, allGames)
//            val lossPercentage = CalcUtils.percentage(lostGames, allGames)
//
//            entries.add(PieEntry(wonPercentage, "Victory"))
//            entries.add(PieEntry(lossPercentage, "Loss"))
//
//            val dataSet = PieDataSet(entries, "")
//            val pieChartData = PieData(dataSet)
//
//            dataSet.setColors(
//                Color.parseColor("#ff6f00"),
//                Color.parseColor("#1976d2")
//            )
//            dataSet.setDrawValues(false)
//
//            pieChart.animateX(500, Easing.Linear)
//            pieChart.animateY(500, Easing.Linear)
//            pieChart.holeRadius = 0.0f
//            pieChart.transparentCircleRadius = 0.0f
//            pieChart.data = pieChartData
//            pieChart.description.isEnabled = false
//            pieChart.invalidate()
        }
    }
}
