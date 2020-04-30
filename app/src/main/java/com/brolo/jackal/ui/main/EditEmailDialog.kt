package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.fragment_edit_email.*

class EditEmailDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_email, container, false)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            RecyclerView.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {
        fun createInstance(): EditEmailDialog {
            return EditEmailDialog()
        }
    }

}
