package com.brolo.jackal.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.model.MapStatsItemType
import com.brolo.jackal.model.MapStatsListItem
import com.brolo.jackal.utils.BarChartXAxisFormatter
import com.brolo.jackal.utils.BarChartYAxisFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class MapsPlayedAdapter(
    private val data: List<MapStatsListItem>,
    private val games: List<Game>,
    private val maps: List<Map>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_BAR_CHART -> {
                val layout = LayoutInflater.from(parent.context).inflate(
                    R.layout.maps_played_bar_chart,
                    parent,
                    false
                )

                MapsBarChartViewHolder(layout)
            }
            TYPE_MAP_INFO -> {
                val layout = LayoutInflater.from(parent.context).inflate(
                    R.layout.map_overview_card,
                    parent,
                    false
                )

                MapOverviewViewHolder(layout)
            } else -> {
                throw IllegalStateException("MapsPlayedAdapter received an unknown view type.")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        if (item.type == MapStatsItemType.Chart) {
            val entries = arrayListOf<BarEntry>()
            val formatter = BarChartXAxisFormatter(maps)
            val barChart = (holder as MapsBarChartViewHolder).view.findViewById<BarChart>(
                R.id.maps_played_bar_chart
            )

            maps.forEachIndexed { index, map ->
                val wonGames = games.filter { it.didWin() && it.mapId == map.id }
                val lostGames = games.filter { it.didLose() && it.mapId == map.id }

                // Only add games to bar chart when at least one game has been completed on the map.
                if (wonGames.isNotEmpty() || lostGames.isNotEmpty())  {
                    entries.add(
                        BarEntry(
                            index.toFloat(), floatArrayOf(wonGames.size.toFloat(), lostGames.size.toFloat())
                        )
                    )
                }
            }

            val set = BarDataSet(entries, "")

            set.stackLabels = arrayOf("Won", "Lost")

            set.colors = listOf(
                ContextCompat.getColor(context, R.color.colorMaterialOrange),
                ContextCompat.getColor(context, R.color.colorMaterialBlue)
            )

            val data = BarData(set)

            data.barWidth = 0.5f

            data.setDrawValues(false)

            barChart.axisLeft.setDrawGridLines(false)
            barChart.data = data
            barChart.description.isEnabled = false
            barChart.xAxis.valueFormatter = formatter
            barChart.setVisibleXRangeMaximum(5f)
            barChart.invalidate()
            barChart.axisLeft.valueFormatter = BarChartYAxisFormatter()
            barChart.axisRight.setDrawLabels(false)
        } else if (item.type == MapStatsItemType.MapOverview) {
            val overviewViewHolder = holder as MapOverviewViewHolder

            val mapNameTxtView = overviewViewHolder.view.findViewById<TextView>(R.id.map_name)
            val wonCountTextView = overviewViewHolder.view.findViewById<TextView>(R.id.won_count)
            val lostCountTextView = overviewViewHolder.view.findViewById<TextView>(R.id.lost_count)
            val lostBar = overviewViewHolder.view.findViewById<LinearLayout>(R.id.lost_bar)
            val wonBar = overviewViewHolder.view.findViewById<LinearLayout>(R.id.won_bar)

            mapNameTxtView.text = item.map?.name

            if (item.wonCount != null && item.lostCount != null) {
                val totalCount = item.wonCount + item.lostCount
                val wonPercentage = item.wonCount.toFloat() / totalCount.toFloat()
                val lostPercentage = item.lostCount.toFloat() / totalCount.toFloat()

                val lostLayoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    lostPercentage
                )
                val wonLayoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    wonPercentage
                )

                lostBar.layoutParams = lostLayoutParams
                wonBar.layoutParams = wonLayoutParams

                wonCountTextView.text = context.resources.getQuantityString(
                    R.plurals.games_won,
                    item.wonCount,
                    item.wonCount
                )

                lostCountTextView.text = context.resources.getQuantityString(
                    R.plurals.games_lost,
                    item.lostCount,
                    item.lostCount
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]

        return when (item.type) {
            MapStatsItemType.Chart -> TYPE_BAR_CHART
            MapStatsItemType.MapOverview -> TYPE_MAP_INFO
        }
    }

    override fun getItemCount() = data.size

    companion object {
        const val TYPE_BAR_CHART = 0
        const val TYPE_MAP_INFO = 1
    }

}
