package com.brolo.jackal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity(indices = [Index("map_id")], foreignKeys = [ForeignKey(entity = Map::class, parentColumns = ["id"], childColumns = ["map_id"])])
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "starting_team") var startingTeam: String,
    var status: String,
    @ColumnInfo(name = "map_id") var mapId: Int?,
    var createdAt: String? = null
) : Serializable {
    companion object {
        const val TeamAttack = "attack"
        const val TeamDefense = "defense"
    }

    fun didWin(): Boolean = status == "won"

    fun didLose(): Boolean = status == "lost"

    fun isComplete(): Boolean = status != "in_progress"

    fun createdAtTimestamp(): Calendar? {
        return try {
            val calendar =  Calendar.getInstance()
            val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
            calendar.time = sdf.parse(this.createdAt)

            calendar
        } catch (e: ParseException) {
            null
        }
    }
}
