package com.septalfauzan.mindtune.data.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.mindtune.R
import com.septalfauzan.mindtune.data.event.SingleEvent
import com.septalfauzan.mindtune.data.repositories.AuthRepository
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _eventChannel = Channel<SingleEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _isLogged: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.getToken()
            token.isNotEmpty().let {
                if (repository.checkIsTokenValid()) {
                    _isLogged.value = true
                } else {
                    setSpotifyTokenAuth("")
                    _isLogged.value = false
                }
                Log.d(AuthViewModel::class.java.simpleName, "auth token: $token")
            }
        }
    }

    fun setSpotifyTokenAuth(token: String) {
        viewModelScope.launch {
            try {
                repository.setToken(token)
            } catch (e: Exception) {
                _eventChannel.send(SingleEvent.MessageEvent("error: ${e.message}"))
            }
        }
    }

    fun spotifyAuth(context: Activity, requestCode: Int, redirectUri: String) {
        val builder = AuthorizationRequest.Builder(
            context.getString(R.string.SPOTIFY_CLIENT_KEY),
            AuthorizationResponse.Type.TOKEN,
            redirectUri
        )
        builder.setScopes(
            arrayOf(
                "streaming",
                "user-top-read",
                "user-read-recently-played",
                "user-library-read",
                "user-read-private",
                "user-read-email"
            )
        )
        val request = builder.build()
        AuthorizationClient.openLoginInBrowser(context, request)
    }

    fun spotifyLogout(context: Context, openSuccessView: () -> Unit) {
        AuthorizationClient.clearCookies(context)
        setSpotifyTokenAuth("")
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                openSuccessView()
            }
        }
    }
}