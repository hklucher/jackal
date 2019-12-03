package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.brolo.jackal.model.Game

@Dao
interface GameDao {
    // TODO: sort by createdAt instead of id when we add that field
    @Query("SELECT * FROM game ORDER BY id DESC")
    fun getAll(): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Update
    suspend fun update(vararg games: Game)
}
