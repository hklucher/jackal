package com.brolo.jackal.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.brolo.jackal.R
import java.lang.IllegalStateException

class LogGameDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.dialog_log_game, null))
                .setPositiveButton(R.string.create, { dialog, id ->

                })
                .setNegativeButton(R.string.cancel, { dialog, id ->
                    getDialog()?.cancel()
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
