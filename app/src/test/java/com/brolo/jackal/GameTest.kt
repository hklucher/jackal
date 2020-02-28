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

    @Test
    fun didWin_returnsTrueWhenStatusIsWon() {
        game.status = "won"

        assert(game.didWin())
    }

    @Test
    fun didWin_returnsFalseWhenStatusIsInProgress() {
        game.status = "in_progress"

        assert(!game.didWin())
    }

    @Test
    fun didWin_returnsFalseWhenStatusIsLost() {
        game.status = "lost"

        assert(!game.didWin())
    }

    @Test
    fun didLose_returnsTrueWhenStatusIsLost() {
        game.status = "lost"

        assert(game.didLose())
    }

    @Test
    fun didLose_returnsFalseWhenStatusIsWon() {
        game.status = "won"

        assert(!game.didLose())
    }

    @Test
    fun didLose_returnsFalseWhenStatusIsInProgress() {
        game.status = "in_progress"

        assert(!game.didLose())
    }

    @Test
    fun isComplete_returnsFalseWhenStatusIsInProgress() {
        game.status = "in_progress"

        assert(!game.isComplete())
    }

    @Test
    fun isComplete_returnsTrueWhenStatusWon() {
        game.status = "won"

        assert(game.isComplete())
    }

    @Test
    fun isComplete_returnsTrueWhenStatusLost() {
        game.status = "lost"

        assert(game.isComplete())
    }
}
