package com.septalfauzan.mindtune.ui.views.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme

@Composable
fun ArtistCard(
    imageUrl: String,
    artistName: String,
    genres: List<String>,
    modifier: Modifier = Modifier,
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = modifier
                .width(128.dp)
                .heightIn(min = 172.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = imageUrl,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentDescription = artistName,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = artistName,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            )
            Text(
                text = "${genres.joinToString(separator = ", ")}",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.caption.copy(
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MindTuneTheme {
        ArtistCard(
            imageUrl = "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwcm9maWxlLXBhZ2V8MTR8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=400&q=60",
            artistName = "test",
            genres = listOf("Pop", "Rock", "Alternative", "Pop-Punk")
        )
    }
}