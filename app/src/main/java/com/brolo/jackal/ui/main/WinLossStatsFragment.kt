package com.brolo.jackal.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brolo.jackal.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_win_loss_stats.*

class WinLossStatsFragment : Fragment() {

    companion object {
        fun getInstance(): WinLossStatsFragment {
            return WinLossStatsFragment()
        }
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

        setupWinLossPieChart()
    }

    private fun setupWinLossPieChart() {
        val entries = mutableListOf<PieEntry>()
        val pieChart = pie_chart_win_loss

        if (pieChart != null) {
            val wonPercentage = 50f
            val lostPercentage = 50f

            // Add entry for won games
            entries.add(PieEntry(wonPercentage, "Won"))
            // Add entry for lost games
            entries.add(PieEntry(lostPercentage, "Lost"))

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
