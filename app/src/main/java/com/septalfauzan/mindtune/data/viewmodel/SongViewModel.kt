package com.septalfauzan.mindtune.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.mindtune.data.event.SingleEvent
import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TopSongListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TrackResponse
import com.septalfauzan.mindtune.data.repositories.SongsRepository
import com.septalfauzan.mindtune.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(private val songsRepository: SongsRepository) :
    ViewModel() {

    private val _eventChannel = Channel<SingleEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _topTracks: MutableStateFlow<UiState<TopSongListResponse>> =
        MutableStateFlow(UiState.Loading)
    private val _topArtist: MutableStateFlow<UiState<TopArtistListResponse>> =
        MutableStateFlow(UiState.Loading)
    private val _track: MutableStateFlow<UiState<TrackResponse>> =
        MutableStateFlow(UiState.Loading)

    val topTracks: StateFlow<UiState<TopSongListResponse>> = _topTracks
    val topArtist: StateFlow<UiState<TopArtistListResponse>> = _topArtist
    val track: StateFlow<UiState<TrackResponse>> = _track

    val reloadTopArtist = { _topArtist.value = UiState.Loading }
    val reloadTopTracks = { _topTracks.value = UiState.Loading }
    val reloadTrack = { _track.value = UiState.Loading }

    fun getTopArtist() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            try {
                songsRepository.getTopArtist().catch { e ->
                    _topArtist.value = UiState.Error("error: ${e.message}")
                }.collect {
                    _topArtist.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _topArtist.value = UiState.Error("error: ${e.message}")
            }
        }
    }

    fun getTopTrack() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            try {
                songsRepository.getTopTrack().catch { e ->
                    _topTracks.value = UiState.Error("error: ${e.message}")
                }.collect {
                    _topTracks.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _topTracks.value = UiState.Error("error: ${e.message}")
            }
        }
    }

    fun getTrack(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            try {
                songsRepository.getTrack(id).catch { e ->
                    _track.value = UiState.Error("error: ${e.message}")
                }.collect { data ->
                    _track.value = UiState.Success(data)
                }
            } catch (e: Exception) {
                _track.value = UiState.Error("error: ${e.message}")
            }
        }
    }
}