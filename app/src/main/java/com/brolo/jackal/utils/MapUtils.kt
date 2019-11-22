package com.brolo.jackal.utils

import com.brolo.jackal.model.Map

class MapUtils {

    companion object {
        val ALL_MAPS = listOf(
            Map(0, "Bank"),
            Map(0, "Border"),
            Map(0, "Chalet"),
            Map(0, "Clubhouse"),
            Map(0, "Coastline"),
            Map(0, "Consulate"),
            Map(0, "Kafe Dostoyevsky"),
            Map(0, "Kanal"),
            Map(0, "Oregon"),
            Map(0, "Outback"),
            Map(0, "Villa")
        )

        fun getIdByName(mapName: String, maps: List<Map>): Int? {
            return maps.find { it.name == mapName }?.id
        }
    }
}
