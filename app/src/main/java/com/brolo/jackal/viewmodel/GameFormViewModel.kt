package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameFormViewModel(application: Application) : AndroidViewModel(application) {
    var startTeam: LiveData<String> = MutableLiveData<String>("attack")
}
