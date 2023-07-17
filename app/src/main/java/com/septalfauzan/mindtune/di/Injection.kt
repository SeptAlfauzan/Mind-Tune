package com.septalfauzan.mindtune.di

import android.content.Context
import com.septalfauzan.mindtune.data.datastore.DatastorePreference
import com.septalfauzan.mindtune.data.repositories.AuthRepository
import com.septalfauzan.mindtune.data.repositories.MainRepository
import com.septalfauzan.mindtune.data.repositories.SongsRepository
import com.septalfauzan.mindtune.data.repositories.UserRepository

object Injection {
    inline fun <reified T: MainRepository>provideInjection(context: Context): T {
        val datastore = DatastorePreference.getInstances(context)
        return when(T::class){
            AuthRepository::class -> AuthRepository.getInstance(context, datastore) as T
            UserRepository::class -> UserRepository.getInstance(context, datastore) as T
            SongsRepository::class -> SongsRepository.getInstance(context, datastore) as T
            else -> throw Exception("Invalid repository class")
        }
    }
}