package com.example.weather.common.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @copyright This source code written by Majid Arabi and
 * you don't access to use any part of this in another project
 * or publish that for any person.
 * Date: 1/23/2021 AD
 */
abstract class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        error { "onCreate" }
    }

    override fun onStart() {
        super.onStart()
        error { "onStart" }
    }

    override fun onResume() {
        super.onResume()
        error { "onResume" }
    }

    override fun onRestart() {
        super.onRestart()
        error { "onRestart" }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        error { "onResumeFragments" }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        error { "onConfigurationChanged" }
    }

    override fun onPause() {
        super.onPause()
        error { "onPause" }
    }

    override fun onStop() {
        super.onStop()
        error { "onStop" }
    }

    override fun onDestroy() {
        super.onDestroy()
        error { "onDestroy" }
    }

}