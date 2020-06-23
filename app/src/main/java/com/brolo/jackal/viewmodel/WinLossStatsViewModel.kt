package com.brolo.jackal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ChartMode {
    PieChart,
    LineChart,
}

class WinLossStatsViewModel : ViewModel() {
    val chartDisplay = MutableLiveData(ChartMode.PieChart)

    fun setPieChartMode() {
        chartDisplay.value = ChartMode.PieChart
    }

    fun setLineChartMode() {
        chartDisplay.value = ChartMode.LineChart
    }
}
