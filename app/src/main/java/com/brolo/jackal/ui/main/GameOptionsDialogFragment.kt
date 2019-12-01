package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brolo.jackal.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GameOptionsDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(): GameOptionsDialogFragment {
            return GameOptionsDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_options_dialog, container, false)
    }
}