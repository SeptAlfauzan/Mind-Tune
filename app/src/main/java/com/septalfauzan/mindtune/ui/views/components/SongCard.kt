package com.septalfauzan.mindtune.ui.views.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme

@Composable
fun SongCard(
    imageUrl: String,
    title: String,
    artist: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .width(128.dp)
            .height(256.dp),
        shape = RoundedCornerShape(size = 8.dp),
        elevation = 2.dp
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "$title-$artist",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(156.dp)
                    .clip(shape = RoundedCornerShape(size = 4.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = title, style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = artist,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun Preview() {
    MindTuneTheme {
        SongCard(imageUrl = "test", title = "Song title", artist = "artist", onClick = {})
    }
}