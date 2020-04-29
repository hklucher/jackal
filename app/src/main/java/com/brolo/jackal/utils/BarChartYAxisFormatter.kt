package com.brolo.jackal.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class BarChartYAxisFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        // Don't render a label for floats
        if (value.rem(1.0) > 0) {
            return ""
        }

        return value.toInt().toString()
    }

}
