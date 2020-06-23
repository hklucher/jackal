package com.brolo.jackal.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.brolo.jackal.utils.DateUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*

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
        const val StatusWon = "won"
        const val StatusLost = "lost"
        const val StatusInProgress = "in_progress"
    }

    @ExperimentalStdlibApi
    val humanizedStatus: String
        get() {
            return status.split("_").joinToString(" ") {
                it.capitalize(Locale.ROOT)
            }
        }

    @ExperimentalStdlibApi
    val humanizedStartingTeam: String
        get() {
            return startingTeam.capitalize(Locale.ROOT)
        }

    val formattedCreatedAt: String?
        get() {
            return createdAtTimestamp()?.let {
                DateUtils.formatDate(it)
            }
        }

    fun didWin(): Boolean = status == "won"

    fun didLose(): Boolean = status == "lost"

    fun isComplete(): Boolean = status != "in_progress"

    fun createdAtTimestamp(): LocalDate? {
        val createdAt = this.createdAt ?: return null

        return try {
            // TODO: Issue is that blueprinter sends diff strings?
            val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
            val dt = formatter.parseDateTime(createdAt.replace(" UTC", ""))

            dt.toLocalDate()
        } catch (e: Throwable) {
            null
        }
    }
}
