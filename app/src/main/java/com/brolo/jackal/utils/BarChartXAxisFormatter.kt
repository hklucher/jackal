package com.brolo.jackal.utils

import com.brolo.jackal.model.Map
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class BarChartXAxisFormatter(private val maps: List<Map>) : IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val mapName = maps[value.toInt()].name

        if (mapName.length > 3) {
            return "${maps[value.toInt()].name.slice(0..2)}."
        }

        return mapName
    }

}
