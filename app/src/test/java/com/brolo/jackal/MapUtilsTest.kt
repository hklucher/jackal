package com.brolo.jackal

import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.MapUtils
import org.junit.Test

class MapUtilsTest {

    private val maps = listOf(Map(1, "Outback"), Map(2, "Villa"))

    @Test
    fun getIdByName_returnsTheCorrectId() {
        assert(MapUtils.getIdByName("Villa", maps) == 2)
    }

    @Test
    fun getIdByName_returnsNullWhenNotFound() {
        assert(MapUtils.getIdByName("NoMap", maps) == null)
    }
}
