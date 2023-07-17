package com.septalfauzan.mindtune.ui.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.ui.theme.*
import com.septalfauzan.mindtune.utils.formatTwoDigits

enum class MentalHealthTypes {
    DEPRESSION,
    ANXIETY,
    INSOMNIA
}

@Composable
fun MentalHealthCard(
    score: Int,
    type: MentalHealthTypes,
    openRecommendation: () -> Unit,
    isOverlay: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colorGradient = when (type) {
        MentalHealthTypes.DEPRESSION -> Brush.verticalGradient(
            0f to BlueVarian1,
            1f to Purple
        )
        MentalHealthTypes.ANXIETY -> Brush.verticalGradient(
            0f to Orange,
            1f to Pink
        )
        MentalHealthTypes.INSOMNIA -> Brush.verticalGradient(
            0f to Tosca,
            1f to BlueVarian2
        )
    }
    Card(
        modifier = modifier
            .height(124.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp,
    )
    {
        MentalHealthCardContent(
            score, type, openRecommendation, modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(colorGradient)
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun MentalHealthCardContent(
    score: Int,
    type: MentalHealthTypes,
    openRecommendation: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = when (type) {
        MentalHealthTypes.DEPRESSION -> stringResource(R.string.depression)
        MentalHealthTypes.ANXIETY -> stringResource(R.string.anxiety)
        MentalHealthTypes.INSOMNIA -> stringResource(R.string.insomnia)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = modifier) {
            Text(
                text = "$text \nlevel", style = MaterialTheme.typography.caption.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    fontWeight = FontWeight(700)
                )
            )
            Text(
                text = score.formatTwoDigits(), style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Your $text \nscore level is safe", style = MaterialTheme.typography.h6
            )
            RoundedButton(
                text = "Recommendation",
                onClick = { openRecommendation() },
                type = RoundedButtonType.SECONDARY,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RecommendButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "Button",
    type: MentalHealthTypes
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = when (type) {
                MentalHealthTypes.DEPRESSION -> Tosca
                MentalHealthTypes.ANXIETY -> Yellow
                MentalHealthTypes.INSOMNIA -> Orange
            }
        )
    ) {
        Text(
            text = text, style = MaterialTheme.typography.subtitle2.copy(
                color = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MindTuneTheme {
        Column() {
            MentalHealthCard(
                score = 1,
                modifier = Modifier.zIndex(3f),
                type = MentalHealthTypes.DEPRESSION,
                openRecommendation = {}
            )
        }
    }
}