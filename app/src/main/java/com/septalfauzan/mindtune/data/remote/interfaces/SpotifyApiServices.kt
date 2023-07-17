package com.septalfauzan.mindtune.data.remote.interfaces

import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TopSongListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TrackResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.UserProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApiServices {
    @GET("tracks/{id}")
    suspend fun getTrack(@Header("Authorization") token: String, @Path("id") id: String): TrackResponse
    @GET("me/top/artists?time_range=short_term")
    suspend fun getTopArtist(@Header("Authorization") token: String, @Query("limit") limit: Int): TopArtistListResponse

    @GET("me/top/tracks?time_range=short_term")
    suspend fun getTopSongs(@Header("Authorization") token: String, @Query("limit") limit: Int): TopSongListResponse
    @GET("me")
    suspend fun getUserProfile(@Header("Authorization") token: String) : UserProfileResponse
}