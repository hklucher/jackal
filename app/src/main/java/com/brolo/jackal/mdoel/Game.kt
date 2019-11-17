package com.brolo.jackal.mdoel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "starting_team") var startingTeam: String,
    @ColumnInfo(name = "did_win") val didWin: Boolean?
) {
    companion object {
        const val TeamAttack = "attack"
        const val TeamDefense = "defense"
    }
}
