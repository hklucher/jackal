package com.brolo.jackal.ui.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.brolo.jackal.R
import com.brolo.jackal.model.User
import com.brolo.jackal.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_login.*

class SettingsFragment : PreferenceFragmentCompat() {

    private var currentUser: User? = null
    private var emailPreference: Preference? = null
    private var logoutPreference: Preference? = null

    private lateinit var userViewModel: UsersViewModel

    // Could be better, but we should only ever have one user stored in the DB at a time, so we can
    // assume the first user is the current user.
    private val currentUserObserver = Observer<List<User>> { users ->
        if (users.isNotEmpty()) {
            currentUser = users.first().also { syncSettingsUIFromUser(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        userViewModel.currentUser.observe(viewLifecycleOwner, currentUserObserver)

        emailPreference = findPreference(getString(R.string.preference_email_key))
        logoutPreference = findPreference("logout")

        initClickListeners()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey)
    }

    private fun syncSettingsUIFromUser(user: User) {
        emailPreference?.summary = user.email
    }

    private fun initClickListeners() {
        // Show update email dialog
        emailPreference?.setOnPreferenceClickListener {
            val dialogFragment = EditEmailDialog.createInstance()
            val fragmentTransition = childFragmentManager.beginTransaction()

            fragmentTransition.addToBackStack(null)

            dialogFragment.show(fragmentTransition, "edit_email_dialog")

            true
        }

        logoutPreference?.setOnPreferenceClickListener {
            logout()

            true
        }
    }

    private fun logout() {
        val broadcastIntent = Intent()
        // TODO: If this works extract to some constants class
        broadcastIntent.action = "${context?.packageName}.ACTION_LOGOUT"

        activity?.sendBroadcast(broadcastIntent)
    }

    companion object {
        @Suppress("unused")
        val TAG = SettingsFragment::class.java.simpleName
    }

}
