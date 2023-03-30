package com.rivan.androlabs

import android.app.Application
import com.rivan.androlabs.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] class for Andro Labs
 */
@HiltAndroidApp
class AndroLabsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Sync; the system responsible for keeping data in the app up to date.
        Sync.initialize(context = this)
    }
}