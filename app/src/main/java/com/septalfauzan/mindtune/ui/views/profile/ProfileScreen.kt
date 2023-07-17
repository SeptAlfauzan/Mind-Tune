package com.septalfauzan.mindtune.ui.views.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.data.remote.APIResponse.UserProfileResponse
import com.septalfauzan.mindtune.ui.common.ScreenArguments
import com.septalfauzan.mindtune.ui.common.UiState
import com.septalfauzan.mindtune.ui.theme.MindTuneTheme
import com.septalfauzan.mindtune.ui.views.components.RoundedButton
import com.septalfauzan.mindtune.ui.views.components.RoundedButtonType

@Composable
fun ProfileScreen(
    profileArguments: ScreenArguments.ProfileArguments,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp)
    ) {

        profileArguments.userProfileStateFlow.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    profileArguments.loadUserProfile()
                    Text(text = "loading")
                }
                is UiState.Success -> {
                    ProfileScreenContent(
                        userProfile = uiState.data,
                        logoutAction = profileArguments.logoutAction
                    )
                }
                is UiState.Error -> Text(uiState.errorMessage)
            }
        }
    }
}

@Composable
fun ProfileScreenContent(userProfile: UserProfileResponse, logoutAction: () -> Unit) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = userProfile.images?.let {
                    it[1]!!.url
                } ?: "invalid",
                contentDescription = "user-profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            )
            Column() {
                Text(
                    text = userProfile.displayName ?: "none",
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight(700)
                    )
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${userProfile.followers?.total ?: 0}",
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight(700)
                        )
                    )
                    Text(
                        text = "followers",
                        style = MaterialTheme.typography.body2
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "country",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "${userProfile.country}",
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight(700)
                        )
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "email",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "${userProfile.email}",
                        style = MaterialTheme.typography.body2.copy(
                            fontWeight = FontWeight(700)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        RoundedButton(text = "Logout", type = RoundedButtonType.SECONDARY, onClick = logoutAction, modifier = Modifier.width(88.dp))
        Setting()
    }
}

@Composable
private fun Setting(modifier: Modifier = Modifier) {

    var isChecked: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.appearance), style = MaterialTheme.typography.body1)
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Dark mode",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight(700))
            )
            Switch(checked = isChecked, colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.onBackground,
                checkedTrackColor = MaterialTheme.colors.primary
            ), onCheckedChange = {
                isChecked = !isChecked
            })
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
private fun Preview() {
    MindTuneTheme {
//        ProfileScreen(logoutAction = {}, loadUserProfile = { "user_id" })
    }
}