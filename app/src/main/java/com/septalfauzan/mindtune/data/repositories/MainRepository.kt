package com.septalfauzan.mindtune.data.repositories

import android.content.Context
import android.util.Log
import com.septalfauzan.mindtune.config.ApiConfig
import com.septalfauzan.mindtune.data.datastore.DatastorePreference
import com.septalfauzan.mindtune.data.remote.interfaces.SpotifyApiServices
import kotlinx.coroutines.flow.first

open class MainRepository(
    private val context: Context,
    private val datastore: DatastorePreference,
) {
    protected val spotifyApiServices: SpotifyApiServices = ApiConfig.getSpotifyApiServices()
    suspend fun getToken(): String = datastore.getSpotifyToken().first()

    suspend fun setToken(token: String){
        Log.d("TAG", "setToken: $token")
        datastore.setSpotifyToken(token)
    }

    companion object {
        private val TAG = MainRepository::class.java.simpleName

        @Volatile
        private var INSTANCE: MainRepository? = null

        fun getInstance(
            context: Context,
            datastore: DatastorePreference,
        ): MainRepository = INSTANCE ?: synchronized(this) {
            MainRepository(context, datastore).apply {
                INSTANCE = this
            }
        }
    }
}