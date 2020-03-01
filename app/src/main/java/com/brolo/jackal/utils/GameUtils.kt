package com.brolo.jackal.utils

import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import org.joda.time.LocalDate

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

        @Deprecated(
            "This function no longer needs to be used after re-tooling boolean based status",
            ReplaceWith("Use status getter on game instance instead")
        )
        fun getGameStatus(game: Game): String {
            return game.status
        }

        fun filterWithDate(
            date: LocalDate,
            games: List<Game>,
            callback: (game: Game) -> Boolean
        ): List<Game> {
            return games.filter { game ->
                val gamePlayedAt = game.createdAtTimestamp()

                if (gamePlayedAt != null) {
                    callback(game) &&
                        gamePlayedAt
                            .toDateMidnight()
                            .isEqual(date.toDateMidnight())
                } else {
                    false
                }
            }
        }
    }
}
