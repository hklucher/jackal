package com.brolo.jackal.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GamesListResponse
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
        allGames = MutableLiveData<List<Game>>()

        val apiInstance = ApiInstance.getInstance().create(ApiDataService::class.java)
        val response = apiInstance.getGames()

        response.enqueue(object : Callback<GamesListResponse> {
            override fun onResponse(call: Call<GamesListResponse>, response: Response<GamesListResponse>) {
                allGames.value = response.body()?.data
            }

            override fun onFailure(call: Call<GamesListResponse>, t: Throwable) {
                Log.d("GamesViewModel", t.toString())
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
