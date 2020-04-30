package com.brolo.jackal.repository

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `User` (`id` INTEGER NOT NULL, `email` TEXT NOT NULL, PRIMARY KEY(`id`))")
    }
}
