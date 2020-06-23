package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.brolo.jackal.R
import com.brolo.jackal.databinding.FragmentTeamStatsPieChartBinding
import com.brolo.jackal.model.Game
import com.brolo.jackal.utils.CalcUtils
import com.brolo.jackal.viewmodel.GamesViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class TeamStatsPieChartFragment : Fragment() {
    private var _binding: FragmentTeamStatsPieChartBinding? = null
    private val binding get() = _binding!!

    private val gamesViewModel: GamesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamStatsPieChartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPieChart()
        gamesViewModel.allGames.observe(viewLifecycleOwner, Observer { syncPieChartData(it) })
    }

    private fun setupPieChart() {
        binding.startTeamsPieChart.apply {
            holeRadius = 25.0f
            transparentCircleRadius = 0.0f
            description.isEnabled = false
            legend.isEnabled = false
        }
    }

    private fun syncPieChartData(games: List<Game>) {
        val atkGames = games.filter { it.startingTeam == Game.TeamAttack }
        val defGames = games.filter { it.startingTeam == Game.TeamDefense }
        val atkPercentage = CalcUtils.percentage(atkGames, games)
        val defPercentage = CalcUtils.percentage(defGames, games)
        val entries = mutableListOf<PieEntry>()

        entries.add(PieEntry(atkPercentage, "${atkPercentage.toInt()}%"))
        entries.add(PieEntry(defPercentage, "${defPercentage.toInt()}%"))

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawValues(false)

        context?.let { ctx ->
            dataSet.setColors(
                ContextCompat.getColor(ctx, R.color.colorMaterialOrange),
                ContextCompat.getColor(ctx, R.color.colorMaterialBlue)
            )
        }

        val pieData = PieData(dataSet)

        binding.startTeamsPieChart.apply {
            data = pieData
            invalidate()
        }
    }

}
