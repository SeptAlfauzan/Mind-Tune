package com.septalfauzan.mindtune.data.repositories

import android.content.Context
import com.septalfauzan.mindtune.data.datastore.DatastorePreference
import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TopSongListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TrackResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@ViewModelScoped
class SongsRepository @Inject constructor(@ApplicationContext private val context: Context, private val datastorePreference: DatastorePreference): MainRepository(context, datastorePreference) {
    suspend fun getTopArtist(): Flow<TopArtistListResponse>{
        try {
            val token = super.getToken()
            val result = spotifyApiServices.getTopArtist(token = "Bearer $token", limit = 20)
            return flowOf(result)
        }catch (e: Exception){
            throw e
        }
    }

    suspend fun getTopTrack(): Flow<TopSongListResponse>{
        try {
            val token = super.getToken()
            val result = spotifyApiServices.getTopSongs(token = "Bearer $token", limit = 20)
            return flowOf(result)
        }catch (e: Exception){
            throw e
        }
    }

    suspend fun getTrack(id: String): Flow<TrackResponse>{
        try {
            val token = super.getToken()
            val result = spotifyApiServices.getTrack(token = "Bearer $token", id = id)
            return flowOf(result)
        }catch (e: Exception){
            throw e
        }
    }

    companion object {
        private val TAG = SongsRepository::class.java.simpleName

        @Volatile
        private var INSTANCE: SongsRepository? = null

        fun getInstance(
            context: Context,
            datastore: DatastorePreference,
        ): SongsRepository = INSTANCE ?: synchronized(this) {
            SongsRepository(context, datastore).apply {
                INSTANCE = this
            }
        }
    }
}