package com.brolo.jackal.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.brolo.jackal.BR
import com.brolo.jackal.R
import com.brolo.jackal.databinding.FragmentWinLossStatsBinding
import com.brolo.jackal.extensions.toLocalDates
import com.brolo.jackal.model.Game
import com.brolo.jackal.utils.CalcUtils
import com.brolo.jackal.utils.DateBasedChartFormatter
import com.brolo.jackal.utils.GameUtils
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.WinLossStatsViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.fragment_win_loss_stats.*
import org.joda.time.DateTime
import org.joda.time.Interval

class WinLossStatsFragment : Fragment() {
    private var _binding: FragmentWinLossStatsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesViewModel by activityViewModels()
    private val winLossViewModel: WinLossStatsViewModel by viewModels()

    private val loggedGamesObserver = Observer<List<Game>> {
        setupLineChart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWinLossStatsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeGamesViewModel(viewModel)
        setupChartChips()

        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, winLossViewModel)
    }

    private fun observeGamesViewModel(gamesViewModel: GamesViewModel) {
        gamesViewModel.allGames.observe(viewLifecycleOwner, loggedGamesObserver)
    }

    private fun setupChartChips() {
        win_loss_chart_chips.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.win_loss_pie_chip -> winLossViewModel.setPieChartMode()
                R.id.win_loss_line_chip -> winLossViewModel.setLineChartMode()
            }
        }
    }

    private fun setupLineChart() {
//        val chart = line_chart_win_loss
//        val wonEntries = mutableListOf<Entry>()
//        val lostEntries = mutableListOf<Entry>()
//        val now = DateTime.now()
//        val allGames = viewModel.allGames.value
//        // Get an interval of the past week, adding one day from now as the interval is exclusive at the end
//        val pastWeek = Interval(now.minusDays(6), now.plusDays(1))
//        val lines = mutableListOf<LineDataSet>()
//
//        if (allGames != null) {
//            // Iterate over the last 7 days
//            pastWeek.toLocalDates().forEachIndexed { index, date ->
//                val wonOnDay = GameUtils.filterWithDate(date, allGames) { g -> g.didWin() }
//                val lostOnDay = GameUtils.filterWithDate(date, allGames) { g -> g.didLose() }
//
//                wonEntries.add(Entry(index.toFloat(), wonOnDay.size.toFloat()))
//                lostEntries.add(Entry(index.toFloat(), lostOnDay.size.toFloat()))
//            }
//        }
//
//        val wonDataSet = LineDataSet(wonEntries, "Won Games")
//        val lostDataSet = LineDataSet(lostEntries, "Lost Games")
//
//        // Configure setup for the won data set
//        wonDataSet.color = Color.parseColor("#ff6f00")
//        wonDataSet.lineWidth = 6f
//        wonDataSet.circleRadius = 6f
//        wonDataSet.setDrawCircleHole(false)
//        wonDataSet.setCircleColor(Color.parseColor("#d4d4d4"))
//        wonDataSet.setDrawValues(false)
//        wonDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//
//        // Configure setup for the lost data set
//        lostDataSet.color = Color.parseColor("#1976d2")
//        lostDataSet.lineWidth = 4f
//        lostDataSet.circleRadius = 6f
//        lostDataSet.setDrawCircleHole(false)
//        lostDataSet.setCircleColor(Color.parseColor("#d4d4d4"))
//        lostDataSet.setDrawValues(false)
//        lostDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
//
//        lines.add(wonDataSet)
//        lines.add(lostDataSet)
//
//        // General configuration for the chart
//        chart.description.isEnabled = false
//        chart.axisLeft.setDrawGridLines(false)
//        chart.xAxis.setDrawGridLines(false)
//        chart.xAxis.valueFormatter = DateBasedChartFormatter(pastWeek.toLocalDates().toList())
//        chart.axisRight.setDrawLabels(false)
//        chart.axisLeft.valueFormatter = object : ValueFormatter() {
//            override fun getFormattedValue(value: Float): String {
//                if (value % 1 == 0f) {
//                    return value.toInt().toString()
//                }
//
//                return ""
//            }
//        }
//
//        chart.data = LineData(wonDataSet, lostDataSet)
//
//        chart.invalidate()
    }

    companion object {
        @Suppress("unused")
        val TAG = WinLossStatsFragment::class.java.simpleName

        fun getInstance(): WinLossStatsFragment {
            return WinLossStatsFragment()
        }
    }

}
