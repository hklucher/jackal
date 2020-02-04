package com.brolo.jackal

import com.brolo.jackal.model.User
import org.junit.Test

class UserTest {
    private val user = User(1, "test@example.com")

    @Test
    fun attributes_hasIdReader() {
        assert(user.id == 1)
    }

    @Test
    fun attributes_hasEmailReader() {
        assert(user.email == "test@example.com")
    }
}