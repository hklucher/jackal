package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameOptionType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.game_options_sheet.*

class GameOptionsSheet(
    private val optionListener: OnOptionSelectedListener,
    private val game: Game
) : BottomSheetDialogFragment() {

    interface OnOptionSelectedListener {
        fun onOptionSelected(option: GameOptionType, game: Game)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_options_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOptionListeners()
    }

    private fun setupOptionListeners() {
        mark_as_won_row.setOnClickListener {
            optionListener.onOptionSelected(GameOptionType.MARK_GAME_WON, game)
        }

        mark_as_lost_row.setOnClickListener {
            optionListener.onOptionSelected(GameOptionType.MARK_GAME_LOST, game)
        }

        delete_game_row.setOnClickListener {
            optionListener.onOptionSelected(GameOptionType.DELETE_GAME, game)
        }
    }

}
