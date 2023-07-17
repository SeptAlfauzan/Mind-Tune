package com.septalfauzan.mindtune.data.repositories

import android.content.Context
import android.util.Log
import com.septalfauzan.mindtune.data.datastore.DatastorePreference
import com.septalfauzan.mindtune.data.remote.APIResponse.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class UserRepository(private val context: Context, private val datastorePreference: DatastorePreference): MainRepository(context,datastorePreference) {

    suspend fun getUserProfile(): Flow<UserProfileResponse> {
        try {
            val token = super.getToken()
            val result = super.spotifyApiServices.getUserProfile("Bearer $token")
            Log.d("TAG", "getUserProfile: $result")

            return flowOf(result)
        }catch (e: Exception){
            Log.d("TAG", "getUserProfile: $e")
            throw e
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(
            context: Context,
            datastorePreference: DatastorePreference
        ): UserRepository = INSTANCE ?: synchronized(this) {
            UserRepository(context, datastorePreference).apply {
                INSTANCE = this
            }
        }
    }
}