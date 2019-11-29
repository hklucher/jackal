package com.brolo.jackal.utils

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map

class GameUtils {
    companion object {
        fun getPlayedMap(game: Game, allMaps: List<Map>): Map? {
            return allMaps.find { it.id == game.mapId }
        }

        fun getHumanizedStartingSide(game: Game): String {
            return if (game.startingTeam == Game.TeamAttack) {
                "Started on Attack"
            } else {
                "Started on Defense"
            }
        }

        fun getGameStatus(game: Game): String {
            return when (game.didWin) {
                true -> "Victory"
                false -> "Loss"
                else -> "In Progress"
            }
        }
    }
}
