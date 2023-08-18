package com.septalfauzan.mindtune.ui.views.components

import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class ChartElement(
    val label: String,
    val value: Float,
    val color: Color = Color.Magenta
)

fun animateChart(
    targetValues: List<Float>,
    animates: List<Animatable<Float, AnimationVector1D>>,
    animationScope: CoroutineScope,
    duration: Int = 500
) {
    animates.zip(targetValues).forEach {
        val anim = it.first
        val targetValue = it.second
        animationScope.launch {
            anim.animateTo(
                targetValue = targetValue,
                animationSpec = tween(duration)
            )
        }
    }
}

fun animateChartOnClick(
    targetValues: List<Float>,
    animates: List<Animatable<Float, AnimationVector1D>>,
    animationScope: CoroutineScope,
    indexClicked: Int,
    duration: Int = 500
) {
    animates.zip(targetValues).forEachIndexed { index, pair ->
        val anim = pair.first
        val targetValue = pair.second
        animationScope.launch {
            if (index != indexClicked) {
                anim.animateTo(
                    targetValue = if (indexClicked == -1) targetValue else 0f,
                    animationSpec = tween(duration)
                )
            } else {
                anim.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(duration)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PieChart(
    dataset: List<ChartElement>,
    showLegend: Boolean = false,
    modifier: Modifier = Modifier
) {
//    val backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    val maxSize = 380.dp
    val scale = Resources.getSystem().displayMetrics.density
    val dpToFloat = (10.dp * scale)
    var clickedChartIndex by remember { mutableStateOf(-1) }
    val chartAnimations = remember { dataset.map { Animatable(0f) } }
    val animationScope = rememberCoroutineScope()
    val targetValues = dataset.map { it.value }

    LaunchedEffect(clickedChartIndex) {
        animateChartOnClick(
            targetValues = targetValues,
            animates = chartAnimations,
            animationScope = animationScope,
            indexClicked = clickedChartIndex,
            duration = 750
        )
    }

    LaunchedEffect(Unit) {
        animateChart(
            targetValues = targetValues,
            animates = chartAnimations,
            animationScope = animationScope,
            duration = 750
        )
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(maxSize)
        ) {
            for ((index, itemChart) in dataset.withIndex()) {
                var offset = maxSize * 0.0005f
                var width = maxSize * 0.2f
                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .size((maxSize - (60.dp * index)))
                    .background(Color.Transparent)
                    .clip(CircleShape)
                    .clickable {
                        Log.d("TAG", "PieChart $index: clicked")
                        clickedChartIndex = if (clickedChartIndex != index) index else -1
                    }
                    .drawBehind {
                        val drawSize = size / 1.15f
                        background(
                            componentSize = drawSize,
                            backgroundColor = itemChart.color.copy(alpha = 0.2f),
                            width = (24.dp).toPx(),
                        )
                        chart(
                            componentSize = drawSize,
                            backgroundColor = itemChart.color,
                            width = (24.dp).toPx(),
                            percentage = chartAnimations[index].value
                        )
                    }
                ) {}
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(
                    Alignment.Center
                )
            ) {
                val labelModifier =
                    Modifier
                        .width((maxSize - maxSize * (0.2f * dataset.size)))
                        .padding(8.dp)

                if (clickedChartIndex != -1) {
                    val selectedData = dataset[clickedChartIndex]
                    Text(
                        text = selectedData.label,
                        style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                        modifier = labelModifier,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Text(
                        text = "${selectedData.value}%",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = labelModifier,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                } else {
                    Text(
                        text = "Tap to see",
                        style = MaterialTheme.typography.h5.copy(textAlign = TextAlign.Center),
                        modifier = labelModifier,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }

        if (showLegend) {
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(124.dp), content = {
                items(dataset) { item ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(item.color)
                        )
                        Text(text = item.label)
                    }
                }
            })
        }

    }
}

fun DrawScope.background(componentSize: Size, backgroundColor: Color, width: Float) {
    drawArc(
        color = backgroundColor,
        size = componentSize,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = width,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.chart(componentSize: Size, backgroundColor: Color, width: Float, percentage: Float) {
    val startAngle = 270f
    val sweepAngle = (360f * percentage / 100)
    drawArc(
        color = backgroundColor,
        size = componentSize,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = width,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MindTuneTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                PieChart(
                    dataset = listOf(
                        ChartElement(label = "rock", value = 70f, color = Color.Cyan),
                        ChartElement(label = "long long text", value = 40f, color = Color.Blue),
                        ChartElement(label = "pop", value = 90f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}