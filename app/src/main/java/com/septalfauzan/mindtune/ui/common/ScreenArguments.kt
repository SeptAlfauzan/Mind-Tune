package com.septalfauzan.mindtune.ui.common

import androidx.activity.ComponentActivity
import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TopSongListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TrackResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.UserProfileResponse
import com.septalfauzan.mindtune.data.ui.MentalHealth
import com.septalfauzan.mindtune.ui.views.components.MentalHealthTypes
import kotlinx.coroutines.flow.StateFlow

object ScreenArguments {
    data class HomeArguments(
        val itemList: List<MentalHealth>,
        val openRecommendations: Map<MentalHealthTypes, () -> Unit>,
        val openUserProfile: () -> Unit,
        val loadTopTracks: () -> Unit,
        val topTrackStateFlow: StateFlow<UiState<TopSongListResponse>>,
        val loadTopArtist: () -> Unit,
        val topArtistStateFlow: StateFlow<UiState<TopArtistListResponse>>
    )

    data class AuthArguments(
        val spotifyAuth: (activity: ComponentActivity, REQUEST_CODE: Int, REDIRECT_URI: String) -> Unit
    )

    data class ProfileArguments(
        val logoutAction: () -> Unit,
        val loadUserProfile: () -> Unit,
        val userProfileStateFlow: StateFlow<UiState<UserProfileResponse>>,
    )

    data class RecommendationSongArguments(
        val openDetail: (String) -> Unit,
        val loadTopTracks: () -> Unit,
        val topTrackStateFlow: StateFlow<UiState<TopSongListResponse>>,
    )

    data class DetailSongArguments(
        val trackStateFlow: StateFlow<UiState<TrackResponse>>,
        val loadTrack: (String) -> Unit,
    )
}
