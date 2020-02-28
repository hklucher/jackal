package com.brolo.jackal

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.MapUtils
import org.junit.Test

class MapUtilsTest {

    private val outback = Map(1, "Outback")
    private val villa = Map(2, "Villa")

    private val maps = listOf(outback, villa)

    private val playedGames = listOf(
        Game(1, "attack", "in_progress", outback.id),
        Game(2, "defense", "lost", outback.id),
        Game(3, "attack", "won", villa.id)
    )

    @Test
    fun getIdByName_returnsTheCorrectId() {
        assert(MapUtils.getIdByName("Villa", maps) == 2)
    }

    @Test
    fun getIdByName_returnsNullWhenNotFound() {
        assert(MapUtils.getIdByName("NoMap", maps) == null)
    }

    @Test
    fun getMostPlayedMap_returnsTheMostPlayedMapFromGamesList() {
        val result = MapUtils.getMostPlayedMap(playedGames, maps)

        assert(result?.id == outback.id)
    }
}
