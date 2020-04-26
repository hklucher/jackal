package com.brolo.jackal.ui.main

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.brolo.jackal.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey)
    }
}
