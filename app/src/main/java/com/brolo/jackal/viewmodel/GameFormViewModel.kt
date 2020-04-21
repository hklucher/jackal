package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.brolo.jackal.model.Map

class GameFormViewModel(application: Application) : AndroidViewModel(application) {
    var startTeam: MutableLiveData<String?> = MutableLiveData(null)
    var map: MutableLiveData<Map?> = MutableLiveData(null)
    var submitting: MutableLiveData<Boolean> = MutableLiveData(false)
}
