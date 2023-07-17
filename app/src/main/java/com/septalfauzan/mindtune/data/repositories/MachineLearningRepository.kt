package com.septalfauzan.mindtune.data.repositories

import android.content.Context
import com.septalfauzan.mindtune.data.datastore.DatastorePreference

class MachineLearningRepository(private val context: Context, private val datastorePreference: DatastorePreference): MainRepository(context, datastorePreference) {
}