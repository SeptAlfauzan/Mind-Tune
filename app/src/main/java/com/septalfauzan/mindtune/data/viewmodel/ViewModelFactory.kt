package com.septalfauzan.mindtune.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.septalfauzan.mindtune.data.repositories.AuthRepository
import com.septalfauzan.mindtune.data.repositories.MainRepository
import com.septalfauzan.mindtune.data.repositories.SongsRepository
import com.septalfauzan.mindtune.data.repositories.UserRepository

class ViewModelFactory< R: MainRepository>(private val repository: R): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
            modelClass.isAssignableFrom(SongViewModel::class.java) -> SongViewModel(repository as SongsRepository) as T
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(repository as AuthRepository) as T
            else -> throw java.lang.IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
        }
    }
}