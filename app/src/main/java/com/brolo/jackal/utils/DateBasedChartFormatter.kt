package com.brolo.jackal.utils

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.joda.time.LocalDate

class DateBasedChartFormatter(private val dates: List<LocalDate>) : IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val date = dates[value.toInt()]

        return when (date.dayOfWeek) {
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wed"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            7 -> "Sun"
            else -> ""
        }
    }
}
