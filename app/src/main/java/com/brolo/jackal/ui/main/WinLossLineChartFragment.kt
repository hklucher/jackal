package com.brolo.jackal.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.brolo.jackal.R
import com.brolo.jackal.databinding.FragmentWinLossLineChartBinding
import com.brolo.jackal.extensions.toLocalDates
import com.brolo.jackal.model.Game
import com.brolo.jackal.utils.DateBasedChartFormatter
import com.brolo.jackal.utils.GameUtils
import com.brolo.jackal.viewmodel.GamesViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import org.joda.time.DateTime
import org.joda.time.Interval

class WinLossLineChartFragment : Fragment() {
    private var _binding: FragmentWinLossLineChartBinding? = null
    private val binding get() = _binding!!

    private val gamesViewModel: GamesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWinLossLineChartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLineChart()
        gamesViewModel.allGames.observe(viewLifecycleOwner, Observer { syncLineChartData(it) })
    }

    private fun setupLineChart() {
        binding.winLossLineChart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawLabels(false)
            axisLeft.axisMinimum = 0f
            xAxis.setDrawGridLines(false)
        }
    }

    private fun syncLineChartData(games: List<Game>) {
        val chart = binding.winLossLineChart
        val wonEntries = mutableListOf<Entry>()
        val lostEntries = mutableListOf<Entry>()
        val now = DateTime.now()
        val pastWeek = Interval(now.minusDays(6), now.plusDays(1))
        val lines = mutableListOf<LineDataSet>()

        pastWeek.toLocalDates().forEachIndexed { index, date ->
            val wonOnDay = GameUtils.filterWithDate(date, games) { g -> g.didWin() }
            val lostOnDay = GameUtils.filterWithDate(date, games) { g -> g.didLose() }

            wonEntries.add(Entry(index.toFloat(), wonOnDay.size.toFloat()))
            lostEntries.add(Entry(index.toFloat(), lostOnDay.size.toFloat()))
        }

        val wonDataSet = LineDataSet(wonEntries, "Won Games")
        val lostDataSet = LineDataSet(lostEntries, "Lost Games")

        context?.let { ctx ->
            wonDataSet.apply {
                color = ContextCompat.getColor(ctx, R.color.colorMaterialOrange)
                lineWidth = 6f
                circleRadius = 6f
                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                setDrawCircleHole(false)
                setCircleColor(Color.parseColor("#d4d4d4"))
                setDrawValues(false)
            }

            lostDataSet.apply {
                color = ContextCompat.getColor(ctx, R.color.colorMaterialBlue)
                lineWidth = 6f
                circleRadius = 6f
                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                setDrawCircleHole(false)
                setCircleColor(Color.parseColor("#d4d4d4"))
                setDrawValues(false)
            }
        }

        lines.add(wonDataSet)
        lines.add(lostDataSet)

        chart.apply {
            xAxis.valueFormatter = DateBasedChartFormatter(pastWeek.toLocalDates().toList())
            axisLeft.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    if (value % 1 == 0f) {
                        return value.toInt().toString()
                    }

                    return ""
                }
            }
            data = LineData(wonDataSet, lostDataSet)
            invalidate()
        }
    }
}
