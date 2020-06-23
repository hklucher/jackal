package com.brolo.jackal.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.brolo.jackal.BR
import com.brolo.jackal.R
import com.brolo.jackal.databinding.NewGameBottomSheetBinding
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import com.brolo.jackal.model.Map
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import com.brolo.jackal.viewmodel.NewGameViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewGameBottomSheet : BottomSheetDialogFragment() {
    private var _binding: NewGameBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val newGameViewModel: NewGameViewModel by viewModels()
    private val mapsViewModel: MapsViewModel by activityViewModels()
    private val gamesViewModel: GamesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NewGameBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        observedMapsViewModel()
        initMapSpinnerListener()
        initSubmitButton()
    }

    private fun bindViewModel() {
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, newGameViewModel)
    }

    private fun observedMapsViewModel() {
        mapsViewModel.allMaps.observe(viewLifecycleOwner, Observer { maps ->
            val mapNames = maps.map(Map::name)

            context?.let { ctx ->
                binding.mapSelectSpinner.adapter = ArrayAdapter(
                    ctx, R.layout.map_spinner_item, mapNames
                )
            }

            if (newGameViewModel.mapId.value == null) {
                newGameViewModel.mapId.value = maps.first().id
            }
        })
    }

    private fun initMapSpinnerListener() {
        binding.mapSelectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
                newGameViewModel.mapId.value = mapsViewModel.allMaps.value?.get(position)?.id
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                newGameViewModel.mapId.value = null
            }
        }
    }

    private fun getGameRequestFromViewModel(): GameRequest? {
        val startTeam = newGameViewModel.startTeam.value
        val mapId = newGameViewModel.mapId.value

        if (startTeam != null && mapId != null) {
            val game = Game(0, startTeam, Game.StatusInProgress, mapId)

            return GameRequest(game)
        }

        return null
    }

    private fun initSubmitButton() {
        binding.newGameSubmit.setOnClickListener {
            newGameViewModel.submitting.value = true

            getGameRequestFromViewModel()?.let {
                gamesViewModel.createGame(it).observe(viewLifecycleOwner, Observer {
                    newGameViewModel.submitting.value = false

                    dismiss()
                })
            }
        }
    }
}
