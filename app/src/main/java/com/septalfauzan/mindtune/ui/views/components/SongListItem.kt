package com.septalfauzan.mindtune.ui.views.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme

@Composable
fun SongListItem(
    imageUrl: String,
    songTitle: String,
    artist: String,
    album: String,
    duration: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .widthIn(min = 310.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 28.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "$songTitle-$artist",
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp))
                    .background(color = Color(0xFFD9D9D9)),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            )
            SongListItemInfo(songTitle = songTitle, artist = artist, duration = duration, album = album, isFavorite = isFavorite)
        }
    }
}

@Composable
private fun SongListItemInfo(
    songTitle: String,
    artist: String,
    album: String,
    duration: String,
    isFavorite: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.width(72.dp)) {
            Text(
                text = songTitle,
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = artist,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.width(72.dp)) {
            Text(
                text = album,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = duration,
                style = MaterialTheme.typography.caption,
            )
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(R.string.favorite_icon),
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun Preview() {
    MindTuneTheme {
        SongListItem(
            imageUrl = "test",
            songTitle = "Song title",
            artist = "artist",
            album = "album name long long long",
            duration = "03.00",
            isFavorite = true
        )
    }
}