package com.septalfauzan.mindtune.ui.views.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.septalfauzan.mindtune.data.remote.APIResponse.Artist
import com.septalfauzan.mindtune.data.remote.APIResponse.ImagesItem
import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.ui.common.UiState
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import kotlinx.coroutines.flow.StateFlow

val dummyArtists = listOf(
    Artist(
        images = listOf(
            ImagesItem(
                640,
                "https://i.scdn.co/image/ab6761610000e5eb337d671a32b2f44d4a4e6cf2",
                640
            )
        ),
        followers = null,
        genres = null,
        popularity = null,
        name = "null",
        href = null,
        id = null,
        type = null,
        externalUrls = null,
        uri = null
    ),
    Artist(
        images = listOf(
            ImagesItem(
                640,
                "https://i.scdn.co/image/ab6761610000e5eb337d671a32b2f44d4a4e6cf2",
                640
            )
        ),
        followers = null,
        genres = null,
        popularity = null,
        name = "null",
        href = null,
        id = null,
        type = null,
        externalUrls = null,
        uri = null
    ),
    Artist(
        images = listOf(
            ImagesItem(
                640,
                "https://i.scdn.co/image/ab6761610000e5eb337d671a32b2f44d4a4e6cf2",
                640
            )
        ),
        followers = null,
        genres = null,
        popularity = null,
        name = "null",
        href = null,
        id = null,
        type = null,
        externalUrls = null,
        uri = null
    ),
    Artist(
        images = listOf(
            ImagesItem(
                640,
                "https://i.scdn.co/image/ab6761610000e5eb337d671a32b2f44d4a4e6cf2",
                640
            )
        ),
        followers = null,
        genres = null,
        popularity = null,
        name = "null",
        href = null,
        id = null,
        type = null,
        externalUrls = null,
        uri = null
    ),
    Artist(
        images = listOf(
            ImagesItem(
                640,
                "https://i.scdn.co/image/ab6761610000e5eb337d671a32b2f44d4a4e6cf2",
                640
            )
        ),
        followers = null,
        genres = null,
        popularity = null,
        name = "null",
        href = null,
        id = null,
        type = null,
        externalUrls = null,
        uri = null
    ),
)

@Composable
fun TopArtist(
    topArtistStateFlow: StateFlow<UiState<TopArtistListResponse>>,
    loadTopArtist: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val maxCount = 8
    Column(modifier = Modifier.heightIn(min = 124.dp)) {
        topArtistStateFlow.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                    loadTopArtist()
                }
                is UiState.Success -> {
                    val fixedArtist = uiState.data.items?.take(maxCount)
                    fixedArtist?.let { topArtist ->
                        LazyRow(
                            modifier = modifier,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(topArtist) {
                                Column(modifier = Modifier.width(80.dp)) {
                                    AsyncImage(
                                        model = it?.images?.get(0)!!.url,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop,
                                        placeholder = painterResource(id = com.septalfauzan.mindtune.R.drawable.ic_launcher_foreground)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = it.name ?: "None",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> Text(uiState.errorMessage)
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    MindTuneTheme {
//        TopArtist(
//            topArtistStateFlow = dummyArtists
//        )
    }
}

