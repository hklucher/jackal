package com.brolo.jackal.repository

import androidx.lifecycle.LiveData
import com.brolo.jackal.model.User

class UsersRepository(private val userDao: UserDao) {

    val currentUser: LiveData<List<User>> = userDao.currentUser()

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

}
