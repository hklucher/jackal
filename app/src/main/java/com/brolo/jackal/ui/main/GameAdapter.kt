package com.brolo.jackal.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import com.brolo.jackal.databinding.GameCardBinding
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.GameUtils

class GameAdapter(private val games: List<Game>, private val maps: List<Map>, private val listener: OnGameClickListener)
    : RecyclerView.Adapter<GameAdapter.GameHolder>() {

    class GameHolder(val itemBinding: GameCardBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(game: Game, map: Map) {
            itemBinding.game = game
            itemBinding.map = map
        }
    }

    interface OnGameClickListener {
        fun onGameClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val itemBinding = GameCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return GameHolder(itemBinding)
    }

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val game = games[position]
        val map = GameUtils.getPlayedMap(game, maps)

        map?.let { holder.bind(game, map) }

        holder.itemBinding.gameMoreBtn.setOnClickListener { listener.onGameClick(position) }

        setStatusTextStyle(holder, game)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    private fun setStatusTextStyle(holder: GameHolder, game: Game) {
        when {
            game.didWin() -> {
                holder.itemBinding.gameStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.successGreen
                    )
                )
            }

            game.didLose() -> {
                holder.itemBinding.gameStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.errorRed
                    )
                )
            }

            else -> {
                holder.itemBinding.gameStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context, R.color.colorYellowOrange
                    )
                )
            }
        }
    }
}
