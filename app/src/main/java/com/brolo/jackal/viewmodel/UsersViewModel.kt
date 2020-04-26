package com.brolo.jackal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.brolo.jackal.model.User
import com.brolo.jackal.repository.GameDatabase
import com.brolo.jackal.repository.UsersRepository
import kotlinx.coroutines.launch

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UsersRepository

    val currentUser: LiveData<List<User>>

    init {
        val userDao = GameDatabase.getDatabase(application).userDao()

        repository = UsersRepository(userDao)
        currentUser = repository.currentUser
    }

    fun insertCurrentUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

}
