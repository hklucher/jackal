package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class GameMultiSelectViewModel(application: Application) : AndroidViewModel(application) {

    var gameIds: MutableLiveData<List<Int>> = MutableLiveData(mutableListOf())

    fun add(gameId: Int) {
        gameIds.value = gameIds.value?.plus(gameId)
    }

}
