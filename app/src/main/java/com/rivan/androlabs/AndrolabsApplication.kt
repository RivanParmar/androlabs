package com.rivan.androlabs

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [Application] class for Androlabs
 */
@HiltAndroidApp
class AndrolabsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Sync; the system responsible for keeping data in the app up to date.
//        Sync.initialize(context = this)
    }
}