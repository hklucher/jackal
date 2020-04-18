package com.brolo.jackal

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.GameUtils
import org.junit.Test

class GameUtilsTest {
    private val allMaps = listOf(
        Map(1, "Outback")
    )

    private val wonGame = Game(
        0,
        "attack",
        "won",
        allMaps.first().id
    )

    private val lostGame = Game(
        0,
        "defense",
        "lost",
        allMaps.first().id
    )

    private val inProgressGame = Game(
        0,
        "attack",
        "in_progress",
        allMaps.first().id
    )

    @Test
    fun getPlayedMap_returnsPlayedMapWhenFound() {
        val result = GameUtils.getPlayedMap(wonGame, allMaps)

        assert(result == allMaps.first())
    }

    @Test
    fun getHumanizedStartingSide_returnsStartedOnAttackForAttack() {
        val result = GameUtils.getHumanizedStartingSide(wonGame)

        assert(result == "Started on Attack")
    }

    @Test
    fun getHumanizedStartingSide_returnsStartedOnDefenseForDefense() {
        val result = GameUtils.getHumanizedStartingSide(lostGame)

        assert(result == "Started on Defense")
    }

    @ExperimentalStdlibApi
    @Test
    fun getHumanizedStatus_returnsWonWhenWon() {
        val result = GameUtils.getHumanizedStatus(wonGame)

        assert(result == "Won")
    }

    @ExperimentalStdlibApi
    @Test
    fun getHumanizedStatus_returnsLostWhenLost() {
        val result = GameUtils.getHumanizedStatus(lostGame)

        assert(result == "Lost")
    }

    @ExperimentalStdlibApi
    @Test
    fun getHumanizedStatus_returnsInProgressWhenInProgress() {
        val result = GameUtils.getHumanizedStatus(inProgressGame)
        print(result)

        assert(result == "In Progress")
    }
}
