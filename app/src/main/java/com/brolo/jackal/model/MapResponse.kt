package com.brolo.jackal.model

data class MapItem(val attributes: Map)

data class MapResponse(val data: List<MapItem>) {
    fun getMaps(): List<Map> {
        return data.map { it.attributes }
    }
}
