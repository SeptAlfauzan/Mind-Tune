package com.septalfauzan.mindtune.ui.views.songs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import com.septalfauzan.mindtune.ui.views.components.RoundedButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailSongScreen() {
    DetailSongContent()
}

@Composable
fun DetailSongContent() {
    var vibrantColor: Int? by remember {
        mutableStateOf(null)
    }
    var darkVibrantColor: Int? by remember {
        mutableStateOf(null)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colorStops = arrayOf(
                        0f to Color(vibrantColor ?: MaterialTheme.colors.background.hashCode()),
                        0.2f to Color(
                            darkVibrantColor ?: MaterialTheme.colors.background.hashCode()
                        ),
                        1f to MaterialTheme.colors.background
                    ),
                    radius = 1000f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SongCard(
            updateGradientBg = { vibrant, darkVibrant ->
                vibrantColor = vibrant
                darkVibrantColor = darkVibrant
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        RoundedButton(text = stringResource(R.string.play_on_spotify))
    }
}

@Composable
fun SongInfo() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Song name",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = "Artist name", style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun SongCard(
    updateGradientBg: (firstColor: Int, secondColor: Int) -> Unit,
) {
    val url =
        "https://plus.unsplash.com/premium_photo-1688464908902-35c67647df83?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=435&q=80"
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val imageLoader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .allowHardware(false)
                    .target {
                        val result = it
                        val bitmap = (result as BitmapDrawable).bitmap
                        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 164, 164, true)
                        getTwoDominantColors(
                            imageBitmap = resizedBitmap,
                            context = context,
                            updateState = { first, second -> updateGradientBg(first, second) }
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
            shape = RoundedCornerShape(size = 8.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .width(224.dp)
                .height(319.dp)
                .padding(vertical = 32.dp, horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = url,
                modifier = Modifier
                    .width(164.dp)
                    .height(164.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.weight(1f))
            SongInfo()
        }
    }
}

private fun getTwoDominantColors(
    imageBitmap: Bitmap,
    context: Context,
    updateState: (first: Int, second: Int) -> Unit
) {
    val imageBitmapHolder =
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)


    Palette.from(imageBitmap).generate() { pallet ->
        pallet?.let {
            val vibrant = it.getVibrantColor(0x000000)
            val darkVibrant = it.getDarkVibrantColor(0x000000)
            val dominantColor = it.getDominantColor(0xFF1212)
            updateState(vibrant, darkVibrant)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    MindTuneTheme {
        DetailSongScreen()
    }
}
