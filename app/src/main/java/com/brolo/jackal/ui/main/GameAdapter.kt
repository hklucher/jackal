package com.brolo.jackal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.GameUtils

class GameAdapter(
    private val games: List<Game>,
    private val maps: List<Map>
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_card, parent, false) as CardView

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        val mapName = GameUtils.getPlayedMap(game, maps)?.name
        val teamText = GameUtils.getHumanizedStartingSide(game)

        holder.cardView.findViewById<TextView>(R.id.game_title).text = mapName
        holder.cardView.findViewById<TextView>(R.id.game_side).text = teamText
        holder.cardView.findViewById<TextView>(R.id.game_status).text = "In Progress"
    }

    override fun getItemCount(): Int {
        return games.size
    }
}
