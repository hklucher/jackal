package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
        //    TODO: Support repository for offline mode.
        repository = GamesRepository(gamesDao)

        allGames = MutableLiveData<List<Game>>()

        val apiInstance = ApiInstance.getInstance().create(ApiDataService::class.java)
        val request = apiInstance.getLoggedGames()

        request.enqueue(object : Callback<GameListResponse> {
            override fun onResponse(
                call: Call<GameListResponse>,
                response: Response<GameListResponse>
            ) {
                allGames.value = response.body()?.data
            }

            override fun onFailure(call: Call<GameListResponse>, t: Throwable) {
                TODO("not implemented")
            }
        })
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
