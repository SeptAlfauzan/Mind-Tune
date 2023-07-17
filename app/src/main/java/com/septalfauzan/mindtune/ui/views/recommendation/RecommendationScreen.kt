package com.septalfauzan.mindtune.ui.views.recommendation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.mindtune.data.ui.Song
import com.septalfauzan.mindtune.ui.common.ScreenArguments
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import com.septalfauzan.mindtune.ui.views.components.SongCard

private val dummyList: List<Song> = listOf(
    Song(
        id = "1",
        artist = "ACDC",
        song = "Back in Black",
        coverImageUrl = "https://www.billboard.com/wp-content/uploads/media/acdc-back-in-black-album-cover-650.jpg?w=650"
    ),
    Song(
        id = "1",
        artist = "Gorillaz",
        song = "Feel Good Inc.",
        coverImageUrl = "https://static.wikia.nocookie.net/kong/images/f/f3/Gorillazdemondays.jpg/revision/latest?cb=20180620193038"
    ),
    Song(
        id = "1",
        artist = "ACDC",
        song = "Back in Black",
        coverImageUrl = "https://www.billboard.com/wp-content/uploads/media/acdc-back-in-black-album-cover-650.jpg?w=650"
    ),
    Song(
        id = "1",
        artist = "Gorillaz",
        song = "Feel Good Inc.",
        coverImageUrl = "https://static.wikia.nocookie.net/kong/images/f/f3/Gorillazdemondays.jpg/revision/latest?cb=20180620193038"
    ),
    Song(
        id = "1",
        artist = "ACDC",
        song = "Back in Black",
        coverImageUrl = "https://www.billboard.com/wp-content/uploads/media/acdc-back-in-black-album-cover-650.jpg?w=650"
    ),
    Song(
        id = "1",
        artist = "Gorillaz",
        song = "Feel Good Inc.",
        coverImageUrl = "https://static.wikia.nocookie.net/kong/images/f/f3/Gorillazdemondays.jpg/revision/latest?cb=20180620193038"
    ),
    Song(
        id = "1",
        artist = "ACDC",
        song = "Back in Black",
        coverImageUrl = "https://www.billboard.com/wp-content/uploads/media/acdc-back-in-black-album-cover-650.jpg?w=650"
    ),
)

@Composable
fun RecommendationScreen(arguments: ScreenArguments.RecommendationSongArguments, modifier: Modifier = Modifier) {
    RecommendationScreenContent(songs = dummyList, onClick = { arguments.openDetail("") }, modifier = modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecommendationScreenContent(
    songs: List<Song>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cellConfiguration = StaggeredGridCells.Fixed(2)

    Column(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        LazyVerticalStaggeredGrid(
            columns = cellConfiguration,
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 32.dp)
        ) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = "Songs Recommendation",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 32.dp)
                        .width(440.dp),
                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                )
            }
            items(songs) {
                SongCard(
                    imageUrl = it.coverImageUrl,
                    title = it.song,
                    artist = it.artist,
                    onClick = onClick)
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    MindTuneTheme {
        RecommendationScreenContent(dummyList, {})
    }
}