package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import com.brolo.jackal.model.Map

class MapsRepository(mapsDao: MapDao) {
    val maps: LiveData<List<Map>> = mapsDao.getAll()
}
