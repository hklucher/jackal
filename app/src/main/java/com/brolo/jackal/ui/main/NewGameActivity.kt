package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import com.brolo.jackal.model.GameRequest
import com.brolo.jackal.model.GameResponse
import com.brolo.jackal.model.Map
import com.brolo.jackal.network.ApiDataService
import com.brolo.jackal.network.ApiInstance
import com.brolo.jackal.viewmodel.MapsViewModel
import kotlinx.android.synthetic.main.activity_new_game.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO:
// 1. API Call to create game from form
// 2. Once game is created, pass back to previous activity using startActivityForResult
// 3. Style disabled button
class NewGameActivity : AppCompatActivity(R.layout.activity_new_game) {
    companion object {
        @Suppress("unused")
        val TAG = NewGameActivity::class.java.simpleName
    }

    private var selectedTeam: String? = null
    private var selectedMap: Map? = null

    private lateinit var mapsViewModel: MapsViewModel

    private val mapsObserver = Observer<List<Map>> { maps ->
        val mapNames = maps.map(Map::name)
        val currentContext = applicationContext

        if (applicationContext != null) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  Configure the top bar
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.new_game)
        }

        // Configure the maps view model
        mapsViewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        mapsViewModel.allMaps.observe(this, mapsObserver)

        setupRadioButtonListener()
        setupMapSelectListener()
        setupSubmitButton()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }

    private fun setupMapSelectListener() {
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

    private fun setupRadioButtonListener() {
        start_team_radio_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_btn_attack -> {
                    selectedTeam = "attack"
                }
                R.id.radio_btn_defense -> {
                    selectedTeam = "defense"
                }
            }

            updateButtonEnabledState()
        }
    }

    private fun updateButtonEnabledState() {
        submit_game_btn.isEnabled = selectedMap != null && selectedTeam != null
    }

    private fun setupSubmitButton() {
        // NOTE: Need to ensure the IDs stored in Room are the same IDs returned from API
        submit_game_btn.setOnClickListener {
            val game = Game(0, (selectedTeam as String), "in_progress", selectedMap?.id)
            val gameRequest = GameRequest(game)

            val api = ApiInstance.getInstance().create(ApiDataService::class.java)
            val request = api.createGame(gameRequest)

            request.enqueue(object : Callback<GameResponse> {
                override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                    // TODO: Need to figure out why everything is null here
                    // Probably need a custom deserializer or model
                    Log.d(TAG, "onResponse")
                }

                override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure")
                }
            })
        }
    }
}
