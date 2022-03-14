package com.example.weather.application

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDexApplication

import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : MultiDexApplication() {
    companion object {
        var currentActivity: AppCompatActivity? = null
    }
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

}