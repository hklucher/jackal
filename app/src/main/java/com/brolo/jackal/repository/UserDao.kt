package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brolo.jackal.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY id DESC")
    fun currentUser(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

}
