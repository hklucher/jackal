package com.brolo.jackal.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.DateUtils
import com.brolo.jackal.utils.GameUtils
import kotlinx.android.synthetic.main.game_card.view.*

class GameAdapter(
    private val games: List<Game>,
    private val maps: List<Map>,
    private val clickListener: OnGameClickListener,
    private val context: Context?
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView)

    // This GameAdapter class should manage the selectedGameIds
    // We should only listen to changes for selectedGameIds to know when to show icons +
    var selectedGameIds: List<Int> = emptyList()

    interface OnGameClickListener {
        fun onGameClick(position: Int)
        fun onGameLongPress(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_card, parent, false)

        return ViewHolder(cardView)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        val mapName = GameUtils.getPlayedMap(game, maps)?.name
        val teamText = GameUtils.getHumanizedStartingSide(game)
        val createdAt = game.createdAtTimestamp()

        if (selectedGameIds.contains(game.id) && context != null) {
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        }

        holder.cardView.findViewById<TextView>(R.id.game_title).text = mapName
        holder.cardView.findViewById<TextView>(R.id.game_side).text = teamText
        holder.cardView.findViewById<TextView>(R.id.game_status).text = GameUtils.getHumanizedStatus(game)
        holder.cardView.game_more_btn.setOnClickListener { clickListener.onGameClick(position) }
        holder.cardView.game_row_inner_container.setOnLongClickListener {
            clickListener.onGameLongPress(position)
            selectedGameIds = selectedGameIds.plus(game.id)
            notifyDataSetChanged()
            true
        }

        if (createdAt != null) {
            holder.cardView.findViewById<TextView>(R.id.game_created_at).text = DateUtils.formatDate(
                createdAt
            )
        }
        setStatusTextStyle(holder, game)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    private fun setStatusTextStyle(holder: ViewHolder, game: Game) {
        val textView = holder.cardView.findViewById<TextView>(R.id.game_status)

        when {
            game.didWin() -> {
                textView.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.successGreen
                    )
                )
            }

            game.didLose() -> {
                textView.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.errorRed
                    )
                )
            }
            else -> {
                textView.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.colorYellowOrange
                    )
                )
            }
        }
    }
}
