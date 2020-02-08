package com.brolo.jackal.network

import com.brolo.jackal.model.LoginRequest
import com.brolo.jackal.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiDataService {
    // Sign In
    @POST("/users/sign_in")
    fun login(@Body body: LoginRequest): Call<User>

    // Fetch User's profile
    @GET("/users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>
}
