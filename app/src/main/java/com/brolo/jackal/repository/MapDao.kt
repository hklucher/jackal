package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map

@Dao
interface MapDao {
    @Query("SELECT * FROM map")
    fun getAll(): LiveData<List<Map>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: Game)
}
