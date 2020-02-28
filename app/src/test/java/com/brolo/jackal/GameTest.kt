package com.brolo.jackal

import com.brolo.jackal.model.Game
import org.junit.Test

class GameTest {

    private val game = Game(1, "attack", "in_progress", 1)

    @Test
    fun attributes_hasIdReader() {
        assert(game.id == 1)
    }

    @Test
    fun attributes_hasStartingTeamReader() {
        assert(game.startingTeam == "attack")
    }

    @Test
    fun attributes_hasStatusReader() {
        assert(game.status == "in_progress")
    }

    @Test
    fun attributes_hasStatusSetter() {
        game.status = "won"

        assert(game.status == "won")
    }

    @Test
    fun attributes_startingTeamHasSetter() {
        game.startingTeam = Game.TeamDefense

        assert(game.startingTeam == Game.TeamDefense)
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
