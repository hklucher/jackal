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
import com.brolo.jackal.viewmodel.GamesViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_pie_chart.*

class PieChartFragment : Fragment() {

    private lateinit var viewModel: GamesViewModel

    private val gameObserver = Observer<List<Game>> {
        setupChart()
    }

    companion object {
        fun newInstance(): PieChartFragment {
            return PieChartFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pie_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGamesViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.allGames.removeObserver(gameObserver)
    }

    private fun observeGamesViewModel() {
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(GamesViewModel::class.java)
            viewModel.allGames.observe(it, gameObserver)
        }
    }

    private fun setupChart() {
        val entries = mutableListOf<PieEntry>()

        val allGames = viewModel.allGames.value

        allGames?.let {
            val pieChart = pie_chart

            if (pieChart != null) {
                // TOOD: Move percentage caluclations to a utils class
                val defenseGames = it.filter { game -> game.startingTeam == Game.TeamDefense }
                val attackGames = it.filter { game -> game.startingTeam == Game.TeamAttack }
                val defPercentage = (defenseGames.size.toFloat() / allGames.size.toFloat()) * 100
                val atkPercentage = (attackGames.size.toFloat() / allGames.size.toFloat()) * 100

                entries.add(PieEntry(defPercentage, "Defense"))
                entries.add(PieEntry(atkPercentage, "Attack"))

                val dataSet = PieDataSet(entries, "")
                val pieChartData = PieData(dataSet)

                dataSet.setColors(
                    Color.parseColor("#ff6f00"),
                    Color.parseColor("#1976d2")
                )
                dataSet.setDrawValues(false)

                pie_chart.animateX(500, Easing.Linear)
                pie_chart.animateY(500, Easing.Linear)
                pie_chart.holeRadius = 0.0f
                pie_chart.transparentCircleRadius = 0.0f
                pie_chart.data = pieChartData
                pie_chart.description.isEnabled = false
                pie_chart.invalidate()
            }
        }
    }
}
