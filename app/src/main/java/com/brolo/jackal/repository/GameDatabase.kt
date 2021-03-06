package com.brolo.jackal.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.model.User
import com.brolo.jackal.utils.MapUtils

@Database(entities = [Game::class, Map::class, User::class], version = 2, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun mapDao(): MapDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val populateMapsCallback = object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        MapUtils.ALL_MAPS.forEach { map ->
                            db.execSQL(
                                "INSERT INTO map (name) VALUES ('${map.name}')"
                            )
                        }
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).addMigrations(MIGRATION_1_2).addCallback(populateMapsCallback).build()


                INSTANCE = instance

                return instance
            }
        }
    }
}
