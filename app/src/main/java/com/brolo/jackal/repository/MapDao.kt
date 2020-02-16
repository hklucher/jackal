package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map

@Dao
interface MapDao {
    @Query("SELECT * FROM map")
    fun getAll(): LiveData<List<Map>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(vararg maps: Map)
}
