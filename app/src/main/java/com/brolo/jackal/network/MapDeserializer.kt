package com.brolo.jackal.network

import com.brolo.jackal.model.Map
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MapDeserializer : JsonDeserializer<Map> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Map {
        val jsonObject = json?.asJsonObject

        if (jsonObject != null) {
            val mapId = jsonObject.get("id").asInt
            val attributes = jsonObject.get("attributes").asJsonObject
            val mapName = attributes.get("name").asString

            return Map(mapId, mapName)
        } else {
            throw Exception("Don't know how to parse JSON without attributes")
        }
    }
}
