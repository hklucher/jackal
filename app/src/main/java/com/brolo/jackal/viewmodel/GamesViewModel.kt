package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.network.ApiRepository
import com.brolo.jackal.repository.GameDatabase
import com.brolo.jackal.repository.GamesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamesViewModel(application: Application)
    : AndroidViewModel(application) {

    private val repository: GamesRepository
    private val apiRepository = ApiRepository()

    val allGames: LiveData<List<Game>>

    init {
        val gamesDao = GameDatabase.getDatabase(application).gameDao()
        repository = GamesRepository(gamesDao)
        allGames = repository.games

        // TODO: This should be done on app load
        hydrateFromApi()
    }

    // TODO: Need to find a place where this can be called
    // once db has finished being read
    fun hydrateFromApi() {
        val apiInstance = ApiInstance.getInstance().create(ApiDataService::class.java)
        val request = apiInstance.getLoggedGames()

        request.enqueue(object : Callback<List<Game>> {
            override fun onResponse(
                call: Call<List<Game>>,
                response: Response<List<Game>>
            ) {
                val fetchedGames = response.body()

                if (fetchedGames != null) {
                    insertMany(*fetchedGames.toTypedArray())
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                TODO("not implemented")
            }
        })
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(vararg games: Game) {
        viewModelScope.launch {
            repository.insert(*games)
        }
    }

    fun createGame(gameRequest: GameRequest): LiveData<Game> {
        return liveData(Dispatchers.IO) {
            val response: Game = apiRepository.createGame(gameRequest)

            insert(response)

            emit(response)
        }
    }

    fun insert(game: Game) {
        viewModelScope.launch {
            repository.insert(game)
        }
    }

    fun updateGame(gameRequest: GameRequest): LiveData<Game> {
        return liveData(Dispatchers.IO) {
            val response: Game = apiRepository.updateGame(gameRequest)

            update(gameRequest.game)

            emit(response)
        }
    }

    private fun update(game: Game) {
        viewModelScope.launch {
            repository.update(game)
        }
    }

    fun deleteGame(game: Game): LiveData<Response<Unit>> {
        return liveData(Dispatchers.IO) {
            val response = apiRepository.deleteGame(game)

            delete(game)

            emit(response)
        }
    }

    fun delete(game: Game) {
        viewModelScope.launch {
            repository.delete(game)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
