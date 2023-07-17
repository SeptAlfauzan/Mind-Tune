package com.septalfauzan.mindtune.data.repositories

import android.content.Context
import com.septalfauzan.mindtune.data.datastore.DatastorePreference
import retrofit2.HttpException

class AuthRepository(private val context: Context, private val datastorePreference: DatastorePreference): MainRepository(context,datastorePreference) {

    suspend fun checkIsTokenValid(): Boolean {
        return try {
            val token = super.getToken()
            super.spotifyApiServices.getUserProfile("Bearer $token")
            true
        }catch (e: HttpException){
            !(e.code() == 401 || e.code() == 400)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null

        fun getInstance(
            context: Context,
            datastorePreference: DatastorePreference
        ): AuthRepository = INSTANCE ?: synchronized(this) {
            AuthRepository(context, datastorePreference).apply {
                INSTANCE = this
            }
        }
    }
}