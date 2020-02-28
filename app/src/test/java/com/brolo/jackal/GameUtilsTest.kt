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
        "inProgress",
        allMaps.first().id
    )

    @Test
    fun getPlayedMap_returnsPlayedMapWhenFound() {
        val result = GameUtils.getPlayedMap(wonGame, allMaps)

        assert(result == allMaps.first())
    }

    @Test
    fun getGameStatus_returnsInProgressWhenDidWinIsNull() {
        val result = GameUtils.getGameStatus(inProgressGame)

        assert(result == "In Progress")
    }

    @Test
    fun getGameStatus_returnsVictoryWhenDidWinIsTrue() {
        val result = GameUtils.getGameStatus(wonGame)

        assert(result == "Victory")
    }

    @Test
    fun getGameStatus_returnsLossWhenDidWinIsFalse() {
        val result = GameUtils.getGameStatus(lostGame)

        assert(result == "Loss")
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
}
