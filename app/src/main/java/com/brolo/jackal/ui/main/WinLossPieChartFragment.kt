package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.brolo.jackal.BR
import com.brolo.jackal.R
import com.brolo.jackal.databinding.FragmentWinLossPieChartBinding
import com.brolo.jackal.model.Game
import com.brolo.jackal.utils.CalcUtils
import com.brolo.jackal.viewmodel.GamesViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

// NOTE: Compiler thinks this is unused because as of now it's only used in FragmentContainerView
@Suppress("unused")
class WinLossPieChartFragment : Fragment() {
    private var _binding: FragmentWinLossPieChartBinding? = null
    private val binding get() = _binding!!

    private val gamesViewModel: GamesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWinLossPieChartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPieChart()

        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, gamesViewModel)

        gamesViewModel.allGames.observe(viewLifecycleOwner, Observer {
            syncPieChartData(it)
        })
    }

    private fun setupPieChart() {
        binding.winLossPieChart.apply {
            holeRadius = 25.0f
            transparentCircleRadius = 0.0f
            description.isEnabled = false
            legend.isEnabled = false
        }
    }

    private fun syncPieChartData(games: List<Game>) {
        val pieChart = binding.winLossPieChart
        val completedGames = games.filter { it.isComplete() }
        val wonGames = completedGames.filter { it.didWin() }
        val lostGames = completedGames.filter { it.didLose() }
        val wonPercentage = CalcUtils.percentage(wonGames, completedGames)
        val lostPercentage = CalcUtils.percentage(lostGames, completedGames)
        val entries = mutableListOf<PieEntry>()

        entries.add(PieEntry(wonPercentage, "${wonPercentage.toInt()}%"))
        entries.add(PieEntry(lostPercentage, "${lostPercentage.toInt()}%"))

        val dataSet = PieDataSet(entries, "")
        val pieData = PieData(dataSet)

        context?.let { ctx ->
            dataSet.setColors(
                ContextCompat.getColor(ctx, R.color.colorMaterialOrange),
                ContextCompat.getColor(ctx, R.color.colorMaterialBlue)
            )

            dataSet.setDrawValues(false)
        }

        pieChart.data = pieData

        pieChart.invalidate()
    }
}
