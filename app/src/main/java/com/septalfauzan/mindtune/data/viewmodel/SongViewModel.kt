package com.septalfauzan.mindtune.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.mindtune.data.event.SingleEvent
import com.septalfauzan.mindtune.data.remote.APIResponse.TopArtistListResponse
import com.septalfauzan.mindtune.data.remote.APIResponse.TopSongListResponse
import com.septalfauzan.mindtune.data.repositories.SongsRepository
import com.septalfauzan.mindtune.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SongViewModel(private val songsRepository: SongsRepository) : ViewModel(){

    private val _eventChannel = Channel<SingleEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _topTracks: MutableStateFlow<UiState<TopSongListResponse>> = MutableStateFlow(UiState.Loading)
    private val _topArtist: MutableStateFlow<UiState<TopArtistListResponse>> = MutableStateFlow(UiState.Loading)
    val topTracks: StateFlow<UiState<TopSongListResponse>> = _topTracks
    val topArtist: StateFlow<UiState<TopArtistListResponse>> = _topArtist

    fun getTopArtist(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                songsRepository.getTopArtist().catch { e ->
                    _topArtist.value = UiState.Error("error: ${e.message}")
                }.collect{
                    _topArtist.value = UiState.Success(it)
                }
            }catch (e: Exception){
                _topArtist.value = UiState.Error("error: ${e.message}")
            }
        }
    }
    fun getTopTrack(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                songsRepository.getTopTrack().catch { e ->
                    _topTracks.value = UiState.Error("error: ${e.message}")
                }.collect{
                    _topTracks.value = UiState.Success(it)
                }
            }catch (e: Exception){
                _topTracks.value = UiState.Error("error: ${e.message}")
            }
        }
    }
}