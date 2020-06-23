package com.brolo.jackal.network

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import retrofit2.Response

class ApiRepository {
    private val client: ApiDataService = ApiInstance.getInstance().create(ApiDataService::class.java)

    suspend fun createGame(gameRequest: GameRequest): Game {
        return client.createGame(gameRequest)
    }

    suspend fun updateGame(gameRequest: GameRequest): Game {
        return client.updateGame(gameRequest.game.id, gameRequest)
    }

    suspend fun deleteGame(game: Game): Response<Unit> {
        return client.deleteGame(game.id)
    }
}
