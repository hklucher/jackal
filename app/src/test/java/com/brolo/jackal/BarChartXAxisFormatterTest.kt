package com.brolo.jackal

import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.BarChartXAxisFormatter
import org.junit.Test

class BarChartXAxisFormatterTest {
    private val maps = listOf(
        Map(1, "Outback"),
        Map(2, "Kanal"),
        Map(3, "Kafe")
    )

    private val formatter = BarChartXAxisFormatter(maps)

    @Test
    fun getFormattedValue_returnsMapNameAtIndex() {
        val result = formatter.getFormattedValue(1.0f)

        assert(result == "Kanal")
    }

    @Test
    fun getFormattedValue_truncatesTo5Characters() {
        val result = formatter.getFormattedValue(0.0f)

        assert(result == "Outba.")
    }
}
