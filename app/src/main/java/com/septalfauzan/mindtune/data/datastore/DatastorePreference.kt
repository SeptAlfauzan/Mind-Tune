package com.septalfauzan.mindtune.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatastorePreference @Inject constructor(@ApplicationContext private val context: Context){
    private val SPOTIFY_TOKEN = stringPreferencesKey("spotify_token")

    fun getSpotifyToken(): Flow<String> = context.datastore.data.map { it[SPOTIFY_TOKEN] ?: "" }

    suspend fun setSpotifyToken(token: String){
        context.datastore.edit { it[SPOTIFY_TOKEN] = token }
    }

    companion object {
        @Volatile
        var INSTANCE: DatastorePreference? = null

        fun getInstances(context: Context): DatastorePreference = INSTANCE ?: synchronized(this) {
            val instance = DatastorePreference(context)
            INSTANCE = instance
            instance
        }
    }
}