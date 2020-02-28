package com.brolo.jackal.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GamePostBody(
    @JsonProperty("map_id")
    val mapId: Int?,
    @JsonProperty("status")
    val status: String,
    @JsonProperty("starting_team")
    val startingTeam: String
)

data class GamePostRequest(val game: GamePostBody)