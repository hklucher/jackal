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
    fun getLoggedGames(): Call<GameListResponse>

    @POST("/api/v1/games")
    fun createGame(@Body gameRequest: GameRequest): Call<GameResponse>

    @PUT("/api/v1/games/{id}")
    fun updateGame(@Path("id") id: Int, @Body gameRequest: GameRequest): Call<GameResponse>

    @DELETE("/api/v1/games/{id}")
    fun deleteGame(@Path("id") id: Int): Call<Response<Void>>

}
