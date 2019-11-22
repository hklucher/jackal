package com.brolo.jackal

import com.brolo.jackal.model.Map
import org.junit.Test

class MapTest {

    private val map = Map(1, "Outback")

    @Test
    fun attributes_hasIdReader() {
        assert(map.id == 1)
    }

    @Test
    fun attributes_hasNameReader() {
        assert(map.name == "Outback")
    }
}
