package com.septalfauzan.mindtune.data.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.septalfauzan.mindtune.data.repositories.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(repository: AuthRepository) : ViewModel(){

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            delay(750)
            _isLoading.value = false
        }
    }
}