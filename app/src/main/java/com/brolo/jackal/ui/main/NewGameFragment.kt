package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import com.brolo.jackal.model.GameResponse
import com.brolo.jackal.model.Map
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.viewmodel.GamesViewModel
import com.brolo.jackal.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.activity_new_game.select_map_spinner
import kotlinx.android.synthetic.main.fragment_new_game.*
import retrofit2.Call
import retrofit2.Response

class NewGameFragment : Fragment() {

    companion object {
        @Suppress("unused")
        val TAG = NewGameFragment::class.java.simpleName
    }

    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var gamesViewModel: GamesViewModel

    private var selectedMap: Map? = null
    private var selectedTeam: String? = null

    private val mapsObserver = Observer<List<Map>> { maps ->
        val mapNames = maps.map(Map::name)
        val currentContext = context

        if (currentContext != null) {
            val adapter = ArrayAdapter(
                currentContext,
                R.layout.map_spinner_item,
                mapNames
            )

            select_map_spinner.adapter = adapter
        }

        // If the user hasn't manually selected a map yet, set selectedMap variable to first game
        selectedMap = selectedMap ?: maps.first()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        mapsViewModel.allMaps.observe(viewLifecycleOwner, mapsObserver)

        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

        setupRadioButtonListener()
        setupMapSelectedListener()
        setupSubmitBtn()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_game, container, false)
    }

    // Sync the value of `selectedTeam` when team radio group changes value.
    private fun setupRadioButtonListener() {
        team_radio_grp.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.team_atk -> {
                    selectedTeam = "attack"
                }
                R.id.team_def -> {
                    selectedTeam = "defense"
                }
            }
        }
    }

    // Sync the value of `selectedMap` when map menu changes value.
    private fun setupMapSelectedListener() {
        select_map_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMap = mapsViewModel.allMaps.value?.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // NOTE: Not currently possible to not select a map. Defaults to first.
                selectedMap = null
            }
        }
    }

    private fun setupSubmitBtn() {
       log_game_submit_btn.setOnClickListener {
           val teamForSubmit = selectedTeam
           val mapForSubmit = selectedMap

           if (teamForSubmit != null && mapForSubmit != null) {
               val game = Game(0, teamForSubmit, "in_progress", mapForSubmit.id)
               val gameRequest = GameRequest(game)

               val api = ApiInstance.getInstance().create(ApiDataService::class.java)
               val request = api.createGame(gameRequest)

               request.enqueue(object : retrofit2.Callback<GameResponse> {
                   override fun onResponse(
                       call: Call<GameResponse>,
                       response: Response<GameResponse>
                   ) {
                       try {
                           val createdGame = response.body() as Game

                           // FIXME: There is an issue here where inserting this game
                           // into the DB is swapping team/status fields
                           gamesViewModel.insert(createdGame)

                           Toast.makeText(
                               context,
                               "Successfully logged game!",
                               Toast.LENGTH_LONG
                           ).show()

                           findNavController().navigateUp()
                       } catch (error: TypeCastException) {
                           Toast.makeText(
                               context,
                               "Unfortunately, there was a server error logging this game.",
                               Toast.LENGTH_LONG
                           ).show()

                       }
                   }

                   override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                       Log.d(TAG, t.toString())
                   }
               })
           }
       }
    }
}
