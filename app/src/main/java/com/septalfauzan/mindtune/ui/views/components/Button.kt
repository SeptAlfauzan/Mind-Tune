package com.septalfauzan.mindtune.ui.views.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.mindtune.ui.theme.Black
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme

enum class RoundedButtonType {
    PRIMARY,
    SECONDARY
}

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "Button",
    type: RoundedButtonType = RoundedButtonType.PRIMARY
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = if(type == RoundedButtonType.PRIMARY) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground)
    ) {
        Text(
            text = text, style = MaterialTheme.typography.caption.copy(
                color = if (type == RoundedButtonType.PRIMARY) Black else MaterialTheme.colors.background
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MindTuneTheme {
        RoundedButton()
    }
}

