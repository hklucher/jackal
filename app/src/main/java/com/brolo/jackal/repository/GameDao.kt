package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.brolo.jackal.model.Game

@Dao
interface GameDao {
    // TODO: sort by createdAt instead of id when we add that field
    @Query("SELECT * FROM game ORDER BY id DESC")
    fun getAll(): LiveData<List<Game>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg games: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM game")
    suspend fun deleteAll()

    @Update
    suspend fun update(vararg games: Game)
}
