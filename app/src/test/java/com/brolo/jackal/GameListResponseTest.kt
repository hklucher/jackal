package com.brolo.jackal

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameListResponse
import org.junit.Test

class GameListResponseTest {
    private val game = Game(1, "attack", "in_progress", 1)
    private val data = listOf(game)
    private val gameListResponse = GameListResponse(data)

    @Test
    fun hasDataReader() {
        assert(gameListResponse.data == data)
    }
}
