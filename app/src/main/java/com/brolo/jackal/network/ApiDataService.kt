package com.brolo.jackal.network

import com.brolo.jackal.model.*
import com.brolo.jackal.model.Map
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
    @GET("/api/v1/users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    // Fetch a list of the user's games
    @GET("/api/v1/games")
    fun getGames(): Call<GamesListResponse>

    // Fetch all maps
    @GET("/api/v1/maps")
    fun getAllMaps(): Call<MapResponse>
}
