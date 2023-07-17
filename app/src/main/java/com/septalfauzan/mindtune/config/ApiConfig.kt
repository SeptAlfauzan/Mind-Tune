package com.septalfauzan.mindtune.config

import com.septalfauzan.mindtune.data.remote.interfaces.SpotifyApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object{
        private val logginInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder()
            .addInterceptor(logginInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        private val retrofit = Retrofit.Builder().apply {
            baseUrl("https://api.spotify.com/v1/")
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()

        fun getSpotifyApiServices(): SpotifyApiServices = retrofit.create(SpotifyApiServices::class.java)
    }
}