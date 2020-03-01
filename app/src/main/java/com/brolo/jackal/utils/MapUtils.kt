package com.brolo.jackal.utils

import com.brolo.jackal.model.Game
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
            Map(0, "Villa"),
            Map(0, "Theme Park")
        )

        fun getMapById(mapId: Int, maps: List<Map>): Map? {
            return maps.find { it.id == mapId }
        }

        fun getMapByName(mapName: String, maps: List<Map>): Map? {
            return maps.find { it.name == mapName }
        }

        fun getIdByName(mapName: String, maps: List<Map>): Int? {
            return getMapByName(mapName, maps)?.id
        }

        fun getMostPlayedMap(games: List<Game>, allMaps: List<Map>): Map? {
            val mostPlayedMapId = games.groupBy { it.mapId }.maxBy {
                it.value.size
            }?.value?.first()?.mapId

            if (mostPlayedMapId != null) {
                return getMapById(mostPlayedMapId, allMaps)
            }

            return null
        }
    }
}
