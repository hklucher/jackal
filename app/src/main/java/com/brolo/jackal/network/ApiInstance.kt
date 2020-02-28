package com.brolo.jackal.network

import com.brolo.jackal.model.GameListResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {
    companion object {
        lateinit var retrofit: Retrofit
        private const val BASE_URL = "https://c648feec.ngrok.io"

        fun getInstance(): Retrofit {
            if (!this::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
            }

            return retrofit
        }

        fun setAuthUtility(authToken: String): Retrofit {
            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val request = chain
                                .request()
                                .newBuilder()
                                .addHeader("Authorization", authToken)
                                .build()

                chain.proceed(request)
            }

            val gson = GsonBuilder()
                .registerTypeAdapter(GameListResponse::class.java, GameListDeserializer())
                .create()

            retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(httpClient.build())
                        .build()

            return retrofit
        }
    }
}
