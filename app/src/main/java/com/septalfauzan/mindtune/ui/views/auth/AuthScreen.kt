package com.septalfauzan.mindtune.ui.views.auth

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.septalfauzan.mindtune.MainActivity.Companion.REDIRECT_URI
import com.septalfauzan.mindtune.MainActivity.Companion.REQUEST_CODE
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.ui.common.ScreenArguments
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import com.septalfauzan.mindtune.ui.views.components.RoundedButton
import com.septalfauzan.mindtune.ui.views.components.RoundedButtonType
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

@Composable
fun AuthScreen(
    authArguments: ScreenArguments.AuthArguments,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AuthScreenContent(loginAction = {
        authArguments.spotifyAuth(
            context as ComponentActivity,
            REQUEST_CODE,
            REDIRECT_URI
        )
    })
}

@Composable
fun AuthScreenContent(loginAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(start = 16.dp, end = 16.dp, top = 80.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Column() {
            Text(
                text = "Mind Tune", style = MaterialTheme.typography.h3.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
            )
            Text(
                text = "Music-Powered Mental Wellness",
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight(300),
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }
        Spacer(modifier = Modifier.size(32.dp))
        Image(
            painter = painterResource(id = R.drawable.listening_music),
            contentDescription = "listening to music",
            modifier = Modifier
                .width(320.dp)
                .height(320.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(32.dp))
        RoundedButton(
            type = RoundedButtonType.SECONDARY,
            text = stringResource(R.string.auth),
            onClick = loginAction
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MindTuneTheme {
        AuthScreenContent(loginAction = {})
    }
}
