package com.brolo.jackal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.brolo.jackal.mdoel.Game
import com.brolo.jackal.repository.GamesRepository

class GamesViewModel : ViewModel() {

    private val gamesListObservable = GamesRepository().getGames()

    //  TODO: See if we an just use the actual val here instead of a getter
    fun getGamesListObservable(): LiveData<List<Game>> {
        return gamesListObservable
    }

}
