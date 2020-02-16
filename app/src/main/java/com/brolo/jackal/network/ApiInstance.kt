package com.brolo.jackal.network

import com.brolo.jackal.model.Game
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {
    companion object {
        lateinit var retrofit: Retrofit

        fun getInstance(): Retrofit {
            if (!this::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                            .baseUrl(Constants.API_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
            }

            return retrofit
        }

        fun setAuthUtility(authToken: String): Retrofit {
            val httpClient = OkHttpClient.Builder()
            val gsonBuilder = GsonBuilder()
            val deserializer = GamesListDeserializer()
//            gsonBuilder.registerTypeAdapter(Game::class.java, deserializer)
//            val customGson = gsonBuilder.create()

            val gson = GsonBuilder()
                        .registerTypeAdapter(Map::class.java, MapDeserializer())
                        .registerTypeAdapter(Game::class.java, GamesListDeserializer())
                        .create()

            httpClient.addInterceptor { chain ->
                val request = chain
                                .request()
                                .newBuilder()
                                .addHeader("Authorization", authToken)
                                .build()

                chain.proceed(request)
            }

            retrofit = Retrofit.Builder()
                        .baseUrl(Constants.API_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(httpClient.build())
                        .build()

            return retrofit
        }
    }
}
