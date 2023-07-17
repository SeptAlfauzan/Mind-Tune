package com.septalfauzan.mindtune.data.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.mindtune.data.event.SingleEvent
import com.septalfauzan.mindtune.data.remote.APIResponse.UserProfileResponse
import com.septalfauzan.mindtune.data.repositories.AuthRepository
import com.septalfauzan.mindtune.data.repositories.UserRepository
import com.septalfauzan.mindtune.ui.common.UiState
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _eventChannel = Channel<SingleEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _userProfile: MutableStateFlow<UiState<UserProfileResponse>> =
        MutableStateFlow(UiState.Loading)
    val userProfile: StateFlow<UiState<UserProfileResponse>> = _userProfile

    fun loadUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getUserProfile().catch { e ->
                    _userProfile.value = UiState.Error("error: $e")
                    _eventChannel.send(SingleEvent.MessageEvent("error: $e"))
                }.collect { data ->
                    Log.d("TAG", "loadUserProfile: $data")
                    _userProfile.value = UiState.Success(data)
                }
            } catch (e: Exception) {
                    Log.d("TAG", "loadUserProfile: ngentod")
                _userProfile.value = UiState.Error("error: ${e.message}")
                _eventChannel.send(SingleEvent.MessageEvent("error: $e"))
            }
        }
    }

    fun reload(){
        _userProfile.value = UiState.Loading
    }
}