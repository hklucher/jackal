package com.brolo.jackal.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.brolo.jackal.R
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_pie_chart.*

class PieChartFragment : Fragment() {

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

        setupChart()
    }

    private fun setupChart() {
        val entries = mutableListOf<PieEntry>()

        entries.add(PieEntry(40.0f, "Defense"))
        entries.add(PieEntry(60.0f, "Attack"))

        val dataSet = PieDataSet(entries, "")
        dataSet.setColors(Color.parseColor("#ff6f00"), Color.parseColor("#1976d2"))
        dataSet.setDrawValues(false)

        val pieChartData = PieData(dataSet)

        pie_chart.holeRadius = 0.0f
        pie_chart.transparentCircleRadius = 0.0f
        pie_chart.data = pieChartData
    }
}
