package com.brolo.jackal.model

enum class MapStatsItemType {
    Chart,
    MapOverview,
}

data class MapStatsListItem(
    val map: Map? = null,
    val wonCount: Int? = null,
    val lostCount: Int? = null
)
