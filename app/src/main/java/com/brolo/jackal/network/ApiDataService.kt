package com.brolo.jackal.network

import com.brolo.jackal.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiDataService {

    // *** USER REQUESTS *** //
    @POST("/users/sign_in")
    fun login(@Body body: LoginRequest): Call<User>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    // *** GAME REQUESTS *** //
    @GET("/api/v1/games")
    fun getLoggedGames(): Call<List<Game>>

    @POST("/api/v1/games")
    suspend fun createGame(@Body gameRequest: GameRequest): Game

    @PUT("/api/v1/games/{id}")
    suspend fun updateGame(@Path("id") id: Int, @Body gameRequest: GameRequest): Game

    @DELETE("/api/v1/games/{id}")
    suspend fun deleteGame(@Path("id") id: Int): Response<Unit>

}
