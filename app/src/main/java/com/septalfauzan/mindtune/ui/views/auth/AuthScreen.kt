package com.septalfauzan.mindtune.ui.views.auth

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedButton(
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
