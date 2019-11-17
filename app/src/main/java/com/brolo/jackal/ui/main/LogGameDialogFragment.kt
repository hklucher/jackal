package com.brolo.jackal.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.brolo.jackal.R
import com.brolo.jackal.mdoel.Game
import java.lang.ClassCastException
import java.lang.IllegalStateException

class LogGameDialogFragment : DialogFragment() {
    internal lateinit var listener: LogGameDialogFragmentListener

    val game = Game(0, "attack", null)

    interface LogGameDialogFragmentListener {
        fun onGameCreated(game: Game)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_log_game, null))
                .setPositiveButton(R.string.create, { dialog, id ->
                    listener.onGameCreated(game)
                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                    getDialog()?.cancel()
                })

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
}
