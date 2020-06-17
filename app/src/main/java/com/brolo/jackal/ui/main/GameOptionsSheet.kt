package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.brolo.jackal.databinding.GameOptionsSheetBinding
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import com.brolo.jackal.viewmodel.GamesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class GameOptionsSheet(
    private val game: Game
) : BottomSheetDialogFragment() {

    companion object {
        val TAG = GameOptionsSheet::class.java.simpleName
    }

    private var _binding: GameOptionsSheetBinding? = null
    private val binding get() = _binding!!

    private val gamesViewModel: GamesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GameOptionsSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOptionListeners()
    }

    private fun setupOptionListeners() {
        binding.markAsWonRow.setOnClickListener { updateGameStatus("won") }
        binding.markAsLostRow.setOnClickListener { updateGameStatus("lost") }
        binding.deleteGameRow.setOnClickListener { deleteGame() }
    }

    private fun updateGameStatus(newStatus: String) {
        game.status = newStatus

        val gameRequest = GameRequest(game)

        gamesViewModel.updateGame(gameRequest).observe(viewLifecycleOwner, Observer { dismiss() })
    }

    private fun deleteGame() {
        gamesViewModel.deleteGame(game).observe(viewLifecycleOwner, Observer { dismiss() })
    }
}
