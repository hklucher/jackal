package com.brolo.jackal.model

import androidx.room.*

@Entity(indices = [Index("map_id")], foreignKeys = [ForeignKey(entity = Map::class, parentColumns = ["id"], childColumns = ["map_id"])])
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "starting_team") var startingTeam: String,
    @ColumnInfo(name = "did_win") val didWin: Boolean?,
    @ColumnInfo(name = "map_id") val mapId: Int
) {
    companion object {
        const val TeamAttack = "attack"
        const val TeamDefense = "defense"
    }
}
