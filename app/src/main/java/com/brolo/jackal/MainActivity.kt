package com.brolo.jackal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brolo.jackal.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        supportActionBar?.subtitle = "Track your siege stats"
    }
}
