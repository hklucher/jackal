package com.brolo.jackal.network

import com.brolo.jackal.model.Game
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.Exception
import java.lang.reflect.Type

class GamesListDeserializer : JsonDeserializer<Game> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Game {
        val jsonObject = json?.asJsonObject

        if (jsonObject != null) {
            val gameId = jsonObject.get("id").asInt
            val attributes = jsonObject.get("attributes").asJsonObject
            val status = attributes.get("status").asString
            val startTeam = attributes.get("starting_team").asString
            val mapId = attributes.get("map_id").asInt
            val didWin = status == "won"

            return Game(gameId, startTeam, didWin, mapId)

        } else {
            throw Exception("Don't know how to parse JSON without attributes")
        }
    }
}

