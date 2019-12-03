package com.brolo.jackal.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brolo.jackal.R
import com.brolo.jackal.model.Game

class GameEditActivity : AppCompatActivity() {

    companion object {
        private const val ARG_GAME = "ARG_GAME"

        fun newIntent(context: Context, game: Game): Intent {
            return Intent(context, GameEditActivity::class.java).apply {
                putExtra(ARG_GAME, game)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_edit)

        supportActionBar?.title = getString(R.string.record_game_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
