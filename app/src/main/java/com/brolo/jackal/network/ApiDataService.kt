package com.brolo.jackal.network

import com.brolo.jackal.model.*
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

    @GET("/api/v1/games")
    fun getLoggedGames(): Call<GameListResponse>

    @POST("/api/v1/games")
    fun createGame(@Body gameRequest: GameRequest): Call<Game>
}
