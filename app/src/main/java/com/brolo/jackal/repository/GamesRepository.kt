package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import com.brolo.jackal.model.Game

class GamesRepository(private val gameDao: GameDao) {

    val games: LiveData<List<Game>> = gameDao.getAll()

    suspend fun insert(game: Game) {
        gameDao.insert(game)
    }
}
