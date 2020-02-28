package com.brolo.jackal.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiInstance {
    companion object {
        lateinit var retrofit: Retrofit
        private const val BASE_URL = "https://ebb9c980.ngrok.io"

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

            retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build()

            return retrofit
        }
    }
}
