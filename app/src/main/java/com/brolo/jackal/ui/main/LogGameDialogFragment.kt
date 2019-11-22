package com.brolo.jackal.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.Map
import com.brolo.jackal.utils.MapUtils
import com.brolo.jackal.viewmodel.MapsViewModel
import java.lang.ClassCastException
import kotlinx.android.synthetic.main.dialog_log_game.cancel_btn
import kotlinx.android.synthetic.main.dialog_log_game.log_game_btn
import kotlinx.android.synthetic.main.dialog_log_game.map_spinner

class LogGameDialogFragment : DialogFragment() {

    private lateinit var listener: LogGameDialogFragmentListener

    private lateinit var mapsViewModel: MapsViewModel

    interface LogGameDialogFragmentListener {
        fun onGameCreated(game: Game)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_log_game, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    override fun onResume() {
        super.onResume()

        val params = dialog?.window?.attributes

        //  Set the layout params programatically to make dialog take up full width of parent
        params?.let {
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = WindowManager.LayoutParams.WRAP_CONTENT

            dialog?.window?.attributes = params
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        observeMapsViewModel(mapsViewModel)

        try {
            listener = context as LogGameDialogFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LogGameDialogFragmentListener")
        }
    }

    private fun observeMapsViewModel(viewModel: MapsViewModel) {
        val mapObserver = Observer<List<Map>> {
            val mapNames = it.map { map -> map.name }
            val currentContext = context

            if (currentContext != null) {
                val adapter = ArrayAdapter(
                    currentContext,
                    R.layout.map_spinner_item,
                    mapNames
                )

                map_spinner.adapter = adapter
            }
        }

        viewModel.allMaps.observe(this, mapObserver)
    }

    private fun setupButtons() {
        log_game_btn.setOnClickListener {
            val game = createGameFromForm()

            listener.onGameCreated(game)

            dialog?.dismiss()
        }

        cancel_btn.setOnClickListener {
            dialog?.dismiss()
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

        val game = Game(0, startingTeam, null, null)
        val allMaps = mapsViewModel.allMaps.value

        if (allMaps != null) {
            val mapName = map_spinner.selectedItem.toString()
            val mapId = MapUtils.getIdByName(mapName, allMaps)

            game.mapId = mapId
        }

        return game
    }
}
