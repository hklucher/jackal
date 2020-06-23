package com.brolo.jackal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewGameViewModel : ViewModel() {
    val startTeam = MutableLiveData<String?>(null)
    val mapId = MutableLiveData<Int?>(null)
    val submitting = MutableLiveData(false)

    fun onStartTeamChanged(team: String) {
        startTeam.value = team
    }
}
