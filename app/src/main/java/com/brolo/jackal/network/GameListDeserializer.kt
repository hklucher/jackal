package com.brolo.jackal.network

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameListResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GameListDeserializer : JsonDeserializer<GameListResponse> {

    companion object {
        @Suppress("unused")
        val TAG = GameListDeserializer::class.java.simpleName
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GameListResponse {
        val games = mutableListOf<Game>()

        if (json != null) {
            val dataList = json.asJsonObject.get("data").asJsonArray

            dataList.forEach { gameObj ->
                val attributes = gameObj.asJsonObject.get("attributes").asJsonObject
                val id = attributes.get("id").asInt
                val status = attributes.get("status").asString
                val startingTeam = attributes.get("startingTeam").asString
                val mapId = attributes.get("mapId").asInt

                games.add(Game(id, status, startingTeam, mapId))
            }
        }

        return GameListResponse(games)
    }
}
