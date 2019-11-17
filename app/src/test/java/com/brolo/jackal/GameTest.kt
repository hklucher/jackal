package com.brolo.jackal

import com.brolo.jackal.model.Game
import org.junit.Test

class GameTest {

    private val game = Game(1, "attack", false)

    @Test
    fun attributes_hasIdReader() {
        assert(game.id == 1)
    }

    @Test
    fun attributes_hasStartingTeamReader() {
        assert(game.startingTeam == "attack")
    }

    @Test
    fun attributes_hasDidWinReader() {
        assert(game.didWin == false)
    }

    @Test
    fun attributes_startingTeamHasSetter() {
        game.startingTeam = Game.TeamDefense

        assert(game.startingTeam == Game.TeamDefense)
    }

    @Test
    fun attributes_didWinIsNullable() {
        val inProgressGame = Game(2, "defense", null)

        assert(inProgressGame.didWin == null)
    }

    @Test
    fun teamConstants_AttackReturnsCorrectString() {
        assert(Game.TeamAttack == "attack")
    }

    @Test
    fun teamConstants_DefenseReturnsCorrectString() {
        assert(Game.TeamDefense == "defense")
    }
}
