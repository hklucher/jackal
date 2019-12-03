package com.brolo.jackal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index("map_id")], foreignKeys = [ForeignKey(entity = Map::class, parentColumns = ["id"], childColumns = ["map_id"])])
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "starting_team") var startingTeam: String,
    @ColumnInfo(name = "did_win") val didWin: Boolean?,
    @ColumnInfo(name = "map_id") var mapId: Int?
) : Serializable {
    companion object {
        const val TeamAttack = "attack"
        const val TeamDefense = "defense"
    }
}
