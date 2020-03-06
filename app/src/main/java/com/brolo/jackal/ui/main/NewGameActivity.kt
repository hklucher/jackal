package com.brolo.jackal.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brolo.jackal.R

class NewGameActivity : AppCompatActivity(R.layout.activity_new_game) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.new_game)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }
}
