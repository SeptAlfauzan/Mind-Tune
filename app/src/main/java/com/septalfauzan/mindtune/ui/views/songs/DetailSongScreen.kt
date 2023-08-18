package com.septalfauzan.mindtune.ui.views.songs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.data.remote.APIResponse.TrackResponse
import com.septalfauzan.mindtune.ui.common.ScreenArguments
import com.septalfauzan.mindtune.ui.common.UiState
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import com.septalfauzan.mindtune.ui.views.components.RoundedButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

@Composable
fun DetailSongScreen(id: String, arguments: ScreenArguments.DetailSongArguments) {
    DetailSongContent(
        trackStateFlow = arguments.trackStateFlow,
        loadTrack = { arguments.loadTrack(id) },
    )
}

@Composable
fun DetailSongContent(
    trackStateFlow: StateFlow<UiState<TrackResponse>>,
    loadTrack: () -> Unit,
) {
    var dominantColor: Int? by remember {
        mutableStateOf(null)
    }

    trackStateFlow.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
                loadTrack()
            }
            is UiState.Success -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colorStops = arrayOf(
                                    0f to Color(
                                        dominantColor ?: MaterialTheme.colors.background.hashCode()
                                    ),
                                    0.8f to MaterialTheme.colors.background
                                ),
                                radius = 1000f
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SongCard(
                        imageAlbumUrl = uiState.data.album?.images?.get(0)?.url ?: "None",
                        songTitle = uiState.data.name ?: "None",
                        artist = uiState.data.artists?.joinToString(", ") { it?.name ?: "None" }
                            ?: "None",
                        spotifUrl = "",
                        updateGradientBg = {
                            dominantColor = it
                        }
                    )
                    Spacer(modifier = Modifier.height(28.dp))
                    RoundedButton(text = stringResource(R.string.play_on_spotify))
                }
            }
            is UiState.Error -> Text(text = "error: ${uiState.errorMessage}")
        }
    }
}

@Composable
fun SongInfo(
    songTitle: String,
    artistName: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = songTitle,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = artistName, style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun SongCard(
    updateGradientBg: (color: Int) -> Unit,
    imageAlbumUrl: String,
    songTitle: String,
    artist: String,
    spotifUrl: String,
) {
    //        "https://plus.unsplash.com/premium_photo-1688464908902-35c67647df83?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80"
    val context = LocalContext.current

    LaunchedEffect(imageAlbumUrl) {
        withContext(Dispatchers.IO) {
            try {
                val imageLoader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(imageAlbumUrl)
                    .allowHardware(false)
                    .target {
                        val result = it
                        val bitmap = (result as BitmapDrawable).bitmap
                        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 164, 164, true)
                        getTwoDominantColors(
                            imageBitmap = resizedBitmap,
                            context = context,
                            updateState = { color -> updateGradientBg(color) }
                        )
                    }
                    .build()
                imageLoader.enqueue(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Card(
        modifier = Modifier.background(
            color = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(size = 32.dp)
        ),
        shape = RoundedCornerShape(size = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .width(224.dp)
                .height(319.dp)
                .padding(vertical = 32.dp, horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageAlbumUrl,
                modifier = Modifier
                    .width(164.dp)
                    .height(164.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.weight(1f))
            SongInfo(songTitle, artist)
        }
    }
}

private fun getTwoDominantColors(
    imageBitmap: Bitmap,
    context: Context,
    updateState: (color: Int) -> Unit
) {
//    val imageBitmapHolder =
//        BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)

    Palette.from(imageBitmap).generate() { pallet ->
        pallet?.let {
//            Log.d("TAG", "getTwoDominantColors: ${it.getDominantColor(0x000000)}")
            val vibrant = it.getVibrantColor(0x000000)
            val darkVibrant = it.getDarkVibrantColor(0x000000)
            val dominantColor = it.getDominantColor(0x001212)
            updateState(vibrant)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    MindTuneTheme {
//        DetailSongScreen()
    }
}
