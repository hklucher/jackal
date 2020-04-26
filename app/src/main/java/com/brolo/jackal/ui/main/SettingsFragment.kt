package com.brolo.jackal.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import com.brolo.jackal.R
import com.brolo.jackal.model.User
import com.brolo.jackal.viewmodel.UsersViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var userViewModel: UsersViewModel

    private var currentUser: User? = null

    // Could be better, but we should only ever have one user stored in the DB at a time, so we can
    // assume the first user is the current user.
    private val currentUserObserver = Observer<List<User>> { users ->
        if (users.isNotEmpty()) {
            currentUser = users.first().also {
                syncSettingsFromUser(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        userViewModel.currentUser.observe(viewLifecycleOwner, currentUserObserver)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey)
    }

    private fun syncSettingsFromUser(user: User) {
    }

    companion object {
        @Suppress("unused")
        val TAG = SettingsFragment::class.java.simpleName
    }

}
