package com.brolo.jackal

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import org.junit.Test

class GameRequestTest {
    private val game = Game(0, "attack", "in_progress", 1)
    private val gameRequest = GameRequest(game)

    @Test
    fun attributes_hasGameReader() {
        assert(gameRequest.game == game)
    }
}
