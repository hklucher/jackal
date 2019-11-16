package com.brolo.jackal.mdoel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "starting_team") val startingTeam: String,
    @ColumnInfo(name = "did_win") val didWin: Boolean?
)
