package com.brolo.jackal.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.GameUtils
import kotlinx.android.synthetic.main.game_card.view.*

class GameAdapter(
    private val games: List<Game>,
    private val maps: List<Map>,
    private val clickListener: OnGameClickListener
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView)

    interface OnGameClickListener {
        fun onGameClick(position: Int)
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

        holder.cardView.findViewById<TextView>(R.id.game_title).text = mapName
        holder.cardView.findViewById<TextView>(R.id.game_side).text = teamText
        holder.cardView.findViewById<TextView>(R.id.game_status).text = GameUtils.getHumanizedStatus(game)
        holder.cardView.game_more_btn.setOnClickListener {
            clickListener.onGameClick(position)
        }

        setStatusTextStyle(holder, game)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    private fun setStatusTextStyle(holder: ViewHolder, game: Game) {
        val textView = holder.cardView.findViewById<TextView>(R.id.game_status)

        if (game.didWin()) {
            textView.setTextColor(Color.parseColor("#4BB543"))
        } else if (game.didLose()) {
            textView.setTextColor(Color.parseColor("#A63232"))
        }
    }
}
