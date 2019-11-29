package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.brolo.jackal.model.Game
import com.brolo.jackal.repository.GameDatabase
import com.brolo.jackal.repository.GamesRepository
import kotlinx.coroutines.launch

class GamesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GamesRepository

    val allGames: LiveData<List<Game>>

    init {
        val gamesDao = GameDatabase.getDatabase(application).gameDao()
        repository = GamesRepository(gamesDao)
        allGames = repository.games
    }

    fun insert(game: Game) {
        viewModelScope.launch {
            repository.insert(game)
        }
    }

    fun delete(game: Game) {
        viewModelScope.launch {
            repository.delete(game)
        }
    }
}
