package com.example.electicsintlproject.client

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit





object RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://fcm.googleapis.com/fcm/"



    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}