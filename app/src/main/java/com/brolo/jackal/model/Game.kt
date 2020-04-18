package com.brolo.jackal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.IllegalArgumentException
import org.joda.time.DateTime
import org.joda.time.LocalDate

@Entity(
    indices = [Index("map_id")],
    foreignKeys = [ForeignKey(entity = Map::class,
    parentColumns = ["id"],
    childColumns = ["map_id"]
)])
data class Game(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,
    @ColumnInfo(name = "starting_team")
    @SerializedName("starting_team")
    var startingTeam: String,
    @SerializedName("status")
    var status: String,
    @ColumnInfo(name = "map_id")
    @SerializedName("map_id")
    var mapId: Int?,
    @SerializedName("created_at")
    var createdAt: String? = null
) : Serializable {
    companion object {
        const val TeamAttack = "attack"
        const val TeamDefense = "defense"
    }

    fun didWin(): Boolean = status == "won"

    fun didLose(): Boolean = status == "lost"

    fun isComplete(): Boolean = status != "in_progress"

    fun createdAtTimestamp(): LocalDate? {
        if (this.createdAt == null) {
            return null
        }

        return try {
            DateTime(this.createdAt).toLocalDate()
        } catch (e: Throwable) {
            null
        }
    }
}
