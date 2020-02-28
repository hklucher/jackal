package com.brolo.jackal.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_win_loss_stats.*

class WinLossStatsFragment : Fragment() {

    private lateinit var viewModel: GamesViewModel

    companion object {
        @Suppress("unused")
        val TAG = WinLossStatsFragment::class.java.simpleName

        fun getInstance(): WinLossStatsFragment {
            return WinLossStatsFragment()
        }
    }

    private val loggedGamesObserver = Observer<List<Game>> {
        setupWinLossPieChart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_win_loss_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        observeGamesViewModel(viewModel)
        setupWinLossPieChart()
    }

    private fun observeGamesViewModel(gamesViewModel: GamesViewModel) {
        gamesViewModel.allGames.observe(this, loggedGamesObserver)
    }

    private fun setupWinLossPieChart() {
        val entries = mutableListOf<PieEntry>()
        val pieChart = pie_chart_win_loss
        val allGames = viewModel.allGames.value

        if (pieChart != null && allGames != null) {
            val completedGames = allGames.filter { it.isComplete() }
            val wonGames = completedGames.filter { it.didWin() }
            val lostGames = completedGames.filter { it.didLose() }

            val wonPercentage = CalcUtils.percentage(wonGames, completedGames)
            val lostPercentage = CalcUtils.percentage(lostGames, completedGames)

            // Add entry for won games
            entries.add(PieEntry(wonPercentage, "${wonPercentage.toInt()}% Won"))
            // Add entry for lost games
            entries.add(PieEntry(lostPercentage, "${lostPercentage.toInt()}% Lost"))

            val dataSet = PieDataSet(entries, "")
            val pieChartData = PieData(dataSet)

            pieChart.data = pieChartData
            pieChart.invalidate()

            dataSet.setColors(
                Color.parseColor("#ff6f00"),
                Color.parseColor("#1976d2")
            )

            pieChart.holeRadius = 0.0f
            pieChart.transparentCircleRadius = 0.0f
            pieChart.data = pieChartData
            pieChart.description.isEnabled = false
            dataSet.setDrawValues(false)

        }
    }

}
