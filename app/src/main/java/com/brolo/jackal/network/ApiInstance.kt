package com.brolo.jackal.network

import com.brolo.jackal.utils.Constants
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
                            .baseUrl(Constants.BASE_URL)
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
            val gson = GsonBuilder().create()

            retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(httpClient.build())
                        .build()

            return retrofit
        }
    }
}
