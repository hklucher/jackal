package com.brolo.jackal.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_game_options_dialog.*
import java.lang.ClassCastException

class GameOptionsDialogFragment : BottomSheetDialogFragment() {

    private lateinit var listener: GameOptionsListener

    companion object {
        private const val ARG_GAME_ID = "ARG_GAME_ID"

        fun newInstance(gameId: Int): GameOptionsDialogFragment {
            val args = Bundle().also {
                it.putInt(ARG_GAME_ID, gameId)
            }

            return GameOptionsDialogFragment().apply {
                arguments = args
            }
        }
    }

    interface GameOptionsListener {
        fun onGameDeleted(gameId: Int)
        fun onRecordGameResult(gameId: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_options_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as GameOptionsListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LogGameDialogFragmentListener")
        }
    }

   private fun setupClickListeners() {
        delete_game_row.setOnClickListener {
            val gameId = arguments?.getInt(ARG_GAME_ID)

            dialog?.dismiss()

            if (gameId != null) {
                listener.onGameDeleted(gameId)
            }
        }

       edit_game_row.setOnClickListener {
           val gameId = arguments?.getInt(ARG_GAME_ID)

           dialog?.dismiss()

           if (gameId != null) {
               listener.onRecordGameResult(gameId)
           }
       }
   }
}
