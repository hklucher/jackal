package com.brolo.jackal.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameListResponse
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.repository.GameDatabase
import com.brolo.jackal.repository.GamesRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GamesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GamesRepository

    val allGames: LiveData<List<Game>>

    init {
        val gamesDao = GameDatabase.getDatabase(application).gameDao()
        repository = GamesRepository(gamesDao)
        allGames = repository.games
    }

    // TODO: Need to find a place where this can be called
    // once db has finished being read
    fun hydrateFromApi() {
        val apiInstance = ApiInstance.getInstance().create(ApiDataService::class.java)
        val request = apiInstance.getLoggedGames()

        request.enqueue(object : Callback<GameListResponse> {
            override fun onResponse(
                call: Call<GameListResponse>,
                response: Response<GameListResponse>
            ) {
                val fetchedGames = response.body()?.data

                if (fetchedGames != null) {
                    insertMany(*fetchedGames.toTypedArray())
                }
            }

            override fun onFailure(call: Call<GameListResponse>, t: Throwable) {
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

    fun insert(game: Game) {
        viewModelScope.launch {
            repository.insert(game)
        }
    }

    fun update(game: Game) {
        viewModelScope.launch {
            repository.update(game)
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
