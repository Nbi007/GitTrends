package com.example.gittrends

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GitTrendApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GitTrendApplication.appContext = applicationContext
    }

    companion object {

        lateinit  var appContext: Context

    }
}