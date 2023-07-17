package com.septalfauzan.mindtune.ui.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class TicketShape(
    private val cornerRadius: Dp = 16.dp,
    private val cutOutHeight: Dp = 60.dp,
    private val cutOutWidth: Dp = 145.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            val cornerRadius = with(density) { cornerRadius.toPx() }
            val cutOutHeight = with(density) { cutOutHeight.toPx() }
            val cutOutWidth = with(density) { cutOutWidth.toPx() }

            // Top-left corner
            arcTo(
                rect = Rect(
                    offset = Offset(0f, 0f),
                    size = Size(cornerRadius * 2, cornerRadius * 2)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // Top edge
            lineTo(size.width - cornerRadius, 0f)
            // Top-right corner
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerRadius, 0f),
                    size = Size(cornerRadius * 2, cornerRadius * 2)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // Right edge
            lineTo(size.width - cornerRadius, size.height - cornerRadius)

            // Bottom-right corner
            arcTo(
                rect = Rect(
                    offset = Offset(size.width - cornerRadius, size.height - cornerRadius),
                    size = Size(cornerRadius * 2, cornerRadius * 2)
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 0f,
                forceMoveTo = false
            )

            lineTo(0f, size.height - cornerRadius)
            // Bottom-left corner
            arcTo(
                rect = Rect(
                    offset = Offset(0f, size.height - cornerRadius),
                    size = Size(cornerRadius * 2, cornerRadius * 2)
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -90f,
                forceMoveTo = false
            )
            close()
        })
    }
}