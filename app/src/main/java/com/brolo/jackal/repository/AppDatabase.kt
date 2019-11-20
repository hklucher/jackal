package com.brolo.jackal.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map

@Database(entities = [Game::class, Map::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun mapDao(): MapDao
}
