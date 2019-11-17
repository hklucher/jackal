package com.brolo.jackal.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brolo.jackal.mdoel.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
