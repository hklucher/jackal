package com.brolo.jackal.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.brolo.jackal.R
import com.brolo.jackal.mdoel.Game
import java.lang.ClassCastException
import java.lang.IllegalStateException

class LogGameDialogFragment : DialogFragment() {

    private lateinit var listener: LogGameDialogFragmentListener

    interface LogGameDialogFragmentListener {
        fun onGameCreated(game: Game)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val layout = activity?.layoutInflater?.inflate(R.layout.dialog_log_game, null)

            builder.setView(layout)
                .setPositiveButton(R.string.create) { _, _ ->
                    val game = createGameFromForm()

                    listener.onGameCreated(game)
                }
                .setNegativeButton(R.string.cancel) { _, _ -> dialog?.cancel() }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as LogGameDialogFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LogGameDialogFragmentListener")
        }
    }

    private fun createGameFromForm(): Game {
        val radioGroup = dialog?.findViewById<RadioGroup>(R.id.team_radio_group)

        val startingTeam =
            if (radioGroup?.checkedRadioButtonId == R.id.radio_attack) {
                Game.TeamAttack
            } else {
                Game.TeamDefense
            }

        return Game(0, startingTeam, null)
    }
}
