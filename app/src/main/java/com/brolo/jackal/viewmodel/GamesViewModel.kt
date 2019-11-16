package com.brolo.jackal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brolo.jackal.mdoel.Game
import com.brolo.jackal.repository.GamesRepository

class GamesViewModel : ViewModel() {

    private val gamesListObservable = GamesRepository().getGames()

    fun getGamesListObservable(): MutableLiveData<List<Game>> {
        return gamesListObservable
    }

    fun addGame(game: Game) {
        val dupGamesList = gamesListObservable.value?.toMutableList() ?: arrayListOf()
        dupGamesList.add(game)

        getGamesListObservable().value = dupGamesList
    }

}
