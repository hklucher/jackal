package com.brolo.jackal.repository

import androidx.lifecycle.MutableLiveData
import com.brolo.jackal.mdoel.Game

class GamesRepository {
    fun getGames(): MutableLiveData<List<Game>> {
        val data = MutableLiveData<List<Game>>()

        // TODO: Set actual games here, just loading empty list for right now.

        return data
    }
}