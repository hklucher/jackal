package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import com.brolo.jackal.model.Game

class GamesRepository(private val gameDao: GameDao) {

    val games: LiveData<List<Game>> = gameDao.getAll()

    suspend fun insert(vararg games: Game) {
        gameDao.insert(*games)
    }

    suspend fun update(game: Game) {
        gameDao.update(game)
    }

    suspend fun delete(game: Game) {
        gameDao.delete(game)
    }

    suspend fun deleteAll() {
        gameDao.deleteAll()
    }
}
