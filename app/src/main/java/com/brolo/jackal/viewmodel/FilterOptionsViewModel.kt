package com.brolo.jackal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brolo.jackal.model.FilterOption

class FilterOptionsViewModel() : ViewModel() {

    val allFilterOptions: MutableLiveData<List<FilterOption>> by lazy {
        MutableLiveData<List<FilterOption>>()
    }

    init {
        allFilterOptions.value = listOf(
            FilterOption("starting_side", "Starting Side", true),
            FilterOption("maps", "Maps", false)
        )
    }
}
