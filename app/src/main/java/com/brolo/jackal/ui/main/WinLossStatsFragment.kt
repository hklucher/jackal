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
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_win_loss_stats.*
import org.joda.time.*
import kotlin.time.ExperimentalTime
import kotlin.time.days

fun Interval.toLocalDates(): Sequence<LocalDate> = generateSequence(start) { d ->
    d.plusDays(1).takeIf { it < end }
}.map(DateTime::toLocalDate)

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
        setupLineChart()
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

        // TODO: This is fetching the games every single time you go back to the tab
        // from "Maps" (could have something to do with the fact that the tab is furthest away)
        // look into how lifecycles work here
        observeGamesViewModel(viewModel)
        setupWinLossPieChart()
        setupChartChips()
//        setupLineChart()
    }

    private fun observeGamesViewModel(gamesViewModel: GamesViewModel) {
        gamesViewModel.allGames.observe(this, loggedGamesObserver)
    }

    private fun setupChartChips() {
        win_loss_chart_chips.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.win_loss_pie_chip -> {
                    line_chart_win_loss.visibility = View.GONE
                    pie_chart_win_loss.visibility = View.VISIBLE
                }
                R.id.win_loss_line_chip -> {
                    pie_chart_win_loss.visibility = View.GONE
                    line_chart_win_loss.visibility = View.VISIBLE
                }
            }
        }
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

    private fun setupLineChart() {
        val chart = line_chart_win_loss
        val wonEntries = mutableListOf<Entry>()
        val lostEntries = mutableListOf<Entry>()
        val now = DateTime.now()
        val allGames = viewModel.allGames.value
        val pastWeek = Interval(now.minusDays(7), now)
        val lines = mutableListOf<LineDataSet>()

        // Iterate over the last 7 days
        pastWeek.toLocalDates().forEachIndexed { index, date ->
            // TODO: Extract wonDay/lostDay funcition to utils class
            // Get all the games played on the current date that were won
            val wonOnDay = allGames?.filter { game ->
                val gamePlayedAt = game.createdAtTimestamp()

                if (gamePlayedAt != null) {
                    game.didWin() && gamePlayedAt.toDateMidnight().isEqual(date.toDateMidnight())
                } else {
                    false
                }
            }

            // Get all the games played on the current date that were lost
            val lostOnDay = allGames?.filter { game ->
                val gamePlayedAt = game.createdAtTimestamp()

                if (gamePlayedAt != null) {
                    game.didLose() && gamePlayedAt.toDateMidnight().isEqual(date.toDateMidnight())
                } else {
                    false
                }
            }

            if (wonOnDay != null && lostOnDay != null) {
                wonEntries.add(Entry(index.toFloat(), wonOnDay.size.toFloat()))
                lostEntries.add(Entry(index.toFloat(), lostOnDay.size.toFloat()))
            }


        }

        val wonDataSet = LineDataSet(wonEntries, "Won Games")
        val lostDataSet = LineDataSet(lostEntries, "Lost Games")

        wonDataSet.color = Color.parseColor("#ff6f00")
        lostDataSet.color = Color.parseColor("#1976d2")

        lines.add(wonDataSet)
        lines.add(lostDataSet)

        chart.data = LineData(wonDataSet, lostDataSet)


        chart.invalidate()
    }
}
