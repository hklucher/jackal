package com.brolo.jackal.network

import com.brolo.jackal.model.Game
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GameResponseDeserializer : JsonDeserializer<Game> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Game? {
        json?.let {
            val attributes = json.asJsonObject.get("data").asJsonObject.get("attributes").asJsonObject
            val id = attributes.get("id").asInt
            val status = attributes.get("status").asString
            val startingTeam = attributes.get("startingTeam").asString
            val mapId = attributes.get("mapId").asInt
            val createdAt = attributes.get("createdAt").asString

            return Game(id, status, startingTeam, mapId, createdAt)
        }

        return null
    }
}
