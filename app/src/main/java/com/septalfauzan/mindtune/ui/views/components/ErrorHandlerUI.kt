package com.septalfauzan.mindtune.ui.views.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme

@Composable
fun ErrorHandlerUI(message: String, onRefresh: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message, style = MaterialTheme.typography.caption)
        Button(onClick = onRefresh, shape = RoundedCornerShape(16.dp)) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "refresh icon")
            Spacer(modifier = Modifier.width(8.dp))
            Text("retry")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Preview() {
    MindTuneTheme() {
        ErrorHandlerUI(message = "error 404", onRefresh = { /*TODO*/ })
    }
}