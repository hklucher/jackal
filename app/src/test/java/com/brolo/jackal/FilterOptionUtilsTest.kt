package com.brolo.jackal

import com.brolo.jackal.model.FilterOption
import com.brolo.jackal.utils.FilterOptionUtils
import org.junit.Test

class FilterOptionUtilsTest {

    private val allOptions = listOf(
        FilterOption("pie_chart", "Pie Chart", true),
        FilterOption("bar_chart", "Ba Chart", false)
    )

    @Test
    fun getSelectedOption_returnsOptionMarkedIsSelected() {
        val result = FilterOptionUtils.getSelectedOption(allOptions)

        assert(result?.value == "pie_chart")
    }

    @Test
    fun get_selectedOption_returnsNullWhenNoSelectedOptionFound() {
        val result = FilterOptionUtils.getSelectedOption(emptyList())

        assert(result == null)
    }
}
