package com.brolo.jackal.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brolo.jackal.MainActivity
import com.brolo.jackal.R
import com.brolo.jackal.model.Game
import kotlinx.android.synthetic.main.activity_game_edit.*

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
        setupButtons()

        supportActionBar?.title = getString(R.string.record_game_result)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupButtons() {
        submit_btn.setOnClickListener {
            val returnIntent = Intent()

            val game = intent.extras?.getSerializable(ARG_GAME) as Game
            val didWin = edit_game_radio_group.checkedRadioButtonId == R.id.radio_btn_won

            returnIntent.putExtra(MainActivity.RESULT_GAME_ID, game.id)
            returnIntent.putExtra(MainActivity.RESULT_ARG_DID_WIN, didWin)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
