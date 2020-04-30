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
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_team_stats.*

class TeamStatsFragment : Fragment() {

    private lateinit var gamesViewModel: GamesViewModel

    private val gamesObserver = Observer<List<Game>> {
        setupStartTeamPieChart(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)
        gamesViewModel.allGames.observe(viewLifecycleOwner, gamesObserver)
    }

    private fun setupStartTeamPieChart(allGames: List<Game>) {
        val entries = mutableListOf<PieEntry>()
        val pieChart = team_pie_chart

        if (pieChart != null) {
            val completedGames = allGames.filter { it.isComplete() }
            val atkGames = completedGames.filter { it.startingTeam == Game.TeamAttack }
            val defGames = completedGames.filter { it.startingTeam == Game.TeamDefense }

            val atkPercentage = CalcUtils.percentage(atkGames, allGames)
            val defPercentage = CalcUtils.percentage(defGames, allGames)

            // Add entry for won games
            entries.add(PieEntry(atkPercentage, "${atkPercentage.toInt()}% Started Atk"))
            // Add entry for lost games
            entries.add(PieEntry(defPercentage, "${defPercentage.toInt()}% Started Def"))

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

    companion object {
        fun getInstance(): TeamStatsFragment {
            return TeamStatsFragment()
        }
    }

}
