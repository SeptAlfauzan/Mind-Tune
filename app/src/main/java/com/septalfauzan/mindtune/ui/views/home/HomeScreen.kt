package com.septalfauzan.mindtune.ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TopSongListResponse
import com.septalfauzan.mindtune.data.ui.MentalHealth
import com.septalfauzan.mindtune.ui.common.ScreenArguments
import com.septalfauzan.mindtune.ui.common.UiState
import com.septalfauzan.mindtune.ui.views.components.*
import kotlinx.coroutines.flow.StateFlow

private val dummyList = listOf<MentalHealth>(
    MentalHealth(1, MentalHealthTypes.DEPRESSION),
    MentalHealth(10, MentalHealthTypes.ANXIETY),
    MentalHealth(8, MentalHealthTypes.INSOMNIA),
)

@Composable
fun HomeScreen(
    arguments: ScreenArguments.HomeArguments,
    modifier: Modifier = Modifier,
) {
    HomeScreenContent(
        loadTopArtist = arguments.loadTopArtist,
        loadTopTrack = arguments.loadTopTracks,
        reloadTopArtist = arguments.reloadTopArtist,
        reloadTopTracks = arguments.reloadTopTracks,
        topTrackStateflow = arguments.topTrackStateFlow,
        topArtistStateflow = arguments.topArtistStateFlow,
        itemList = arguments.itemList,
        openRecommendations = arguments.openRecommendations,
        openUserProfile = arguments.openUserProfile,
        modifier = modifier
    )
}

@Composable
fun HomeScreenContent(
    loadTopTrack: () -> Unit,
    loadTopArtist: () -> Unit,
    reloadTopArtist: () -> Unit,
    reloadTopTracks: () -> Unit,
    topTrackStateflow: StateFlow<UiState<TopSongListResponse>>,
    topArtistStateflow: StateFlow<UiState<TopArtistListResponse>>,
    itemList: List<MentalHealth>,
    openRecommendations: Map<MentalHealthTypes, () -> Unit>,
    openUserProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Avatar(
            imageUrl = "https://images.unsplash.com/photo-1689327037425-9556dca42a09?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0fHx8ZW58MHx8fHx8&auto=format&fit=crop&w=400&q=60",
            openUserProfile = openUserProfile
        )
        Text(
            text = "Good Evening",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(32.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(itemList) {
                MentalHealthCard(score = it.score, type = it.type, openRecommendation = {
                    openRecommendations[it.type]?.let { it() }
                })
            }
        }
        ArtistListened(loadTopArtist, reloadTopArtist, topArtistStateflow)
        SongListened(loadTopTrack, reloadTopTracks, topTrackStateflow)
    }
}

@Composable
private fun SongListened(
    loadTopTracks: () -> Unit,
    reloadTopTracks: () -> Unit,
    topTrackStateflow: StateFlow<UiState<TopSongListResponse>>
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Top songs listened",
                style = MaterialTheme.typography.h5
            )
            RoundedButton(text = "See more", type = RoundedButtonType.SECONDARY)
        }
        topTrackStateflow.collectAsState(initial = UiState.Loading).value.let { uiState ->
            Column(modifier = Modifier.heightIn(min = 124.dp)) {
                when (uiState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator()
                        loadTopTracks()
                    }
                    is UiState.Success ->
                        uiState.data.items?.let { songItem ->
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                items(songItem) {
                                    SongListItem(
                                        imageUrl = it?.album?.images?.get(0)?.url ?: "none",
                                        songTitle = it?.name ?: "None",
                                        artist = it?.artists?.joinToString(", ") {
                                            it?.name ?: "None"
                                        } ?: "None",
                                        album = it?.album?.name ?: "None",
                                        duration = (it?.durationMs ?: 0).toString(),
                                        isFavorite = false
                                    )
                                }
                            }
                        } ?: Text(text = "No favorite song yet.")
                    is UiState.Error -> {
                        ErrorHandlerUI(
                            message = uiState.errorMessage,
                            onRefresh = reloadTopTracks,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun ArtistListened(
    loadTopArtist: () -> Unit,
    reloadTopArtist: () -> Unit,
    topArtistStateflow: StateFlow<UiState<TopArtistListResponse>>
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Top artists listened",
                style = MaterialTheme.typography.h5
            )
            RoundedButton(text = "See more", type = RoundedButtonType.SECONDARY)
        }
        TopArtist(
            topArtistStateFlow = topArtistStateflow,
            loadTopArtist = loadTopArtist,
            reloadTopArtist = reloadTopArtist
        )
    }

}

@Composable
private fun Avatar(
    imageUrl: String,
    openUserProfile: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "user-profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .clickable { openUserProfile() },
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
        )
    }
}
