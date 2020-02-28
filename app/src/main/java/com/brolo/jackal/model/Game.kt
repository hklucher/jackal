package com.brolo.jackal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Entity(indices = [Index("map_id")], foreignKeys = [ForeignKey(entity = Map::class, parentColumns = ["id"], childColumns = ["map_id"])])
data class Game(
    @PrimaryKey(autoGenerate = true) @JsonProperty("id") val id: Int = 0,
    @ColumnInfo(name = "starting_team") @JsonProperty("starting_team") var startingTeam: String,
    @ColumnInfo(name = "did_win") var didWin: Boolean?,
    @ColumnInfo(name = "map_id") var mapId: Int?
) : Serializable {
    companion object {
        const val TeamAttack = "attack"
        const val TeamDefense = "defense"
    }

    @JsonProperty("status")
    fun status(): String {
        val won = didWin

        if (didWin == null) {
            return "in_progress"
        }

        return if (won != null && won) "won" else "loss"
    }
}
