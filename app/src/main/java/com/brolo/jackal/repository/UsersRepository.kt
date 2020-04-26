package com.brolo.jackal.repository

import com.brolo.jackal.model.User

class UsersRepository(private val userDao: UserDao) {

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

}
