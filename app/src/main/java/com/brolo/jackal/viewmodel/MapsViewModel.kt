package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.brolo.jackal.model.Map
import com.brolo.jackal.repository.GameDatabase
import com.brolo.jackal.repository.MapsRepository

class MapsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MapsRepository

    val allMaps: LiveData<List<Map>>

    init {
        val mapsDao = GameDatabase.getDatabase(application).mapDao()
        repository = MapsRepository(mapsDao)
        allMaps = repository.maps
    }
}
