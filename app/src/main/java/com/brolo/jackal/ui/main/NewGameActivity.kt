package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import com.brolo.jackal.viewmodel.GamesViewModel
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

    private var isSubmitting = false
    private var selectedTeam: String? = null
    private var selectedMap: Map? = null

    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var gamesViewModel: GamesViewModel

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

        gamesViewModel = ViewModelProviders.of(this).get(GamesViewModel::class.java)

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
        submit_game_btn.isEnabled = selectedMap != null && selectedTeam != null && !isSubmitting
    }

    private fun handleFormSubmitted() {
        isSubmitting = true
        updateButtonEnabledState()
    }

    private fun handleSubmitResponse(successful: Boolean) {
        isSubmitting = false
        updateButtonEnabledState()

        if (successful) {
            Toast.makeText(this, "Game logged!", Toast.LENGTH_LONG).show()

            finish()
        }
    }

    private fun setupSubmitButton() {
        // NOTE: Need to ensure the IDs stored in Room are the same IDs returned from API
        submit_game_btn.setOnClickListener {
            handleFormSubmitted()




            val game = Game(0, (selectedTeam as String), "won", selectedMap?.id)
            val gameRequest = GameRequest(game)

            gamesViewModel.insert(game)

            handleSubmitResponse(true)


//
//            val api = ApiInstance.getInstance().create(ApiDataService::class.java)
//            val request = api.createGame(gameRequest)
//
//            request.enqueue(object : Callback<GameResponse> {
//                override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
//                    handleSubmitResponse(true)
//                }
//
//                override fun onFailure(call: Call<GameResponse>, t: Throwable) {
//                    handleSubmitResponse(false)
//
//                }
//            })
        }
    }
}
