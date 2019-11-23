package com.brolo.jackal

import com.brolo.jackal.model.FilterOption
import org.junit.Test

class FilterOptionTest {

    private val filterOption = FilterOption("pie_chart", "Pie Chart")

    @Test
    fun attributes_hasValueReader() {
        assert(filterOption.value == "pie_chart")
    }

    @Test
    fun attributes_hasLabelReader() {
        assert(filterOption.label == "Pie Chart")
    }
}
