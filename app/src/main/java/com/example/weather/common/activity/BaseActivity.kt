package com.example.weather.common.activity

import com.example.weather.application.App


abstract class BaseActivity : LogActivity() {

    var check =0
    override fun onResume() {
        super.onResume()
        App.currentActivity = this

    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }

    override fun onDestroy() {
        clearReferences()
        super.onDestroy()
    }

    /**
     * clear current activity reference inside [App.currentActivity]
     */
    private fun clearReferences() {
        val currActivity = App.currentActivity
        if (this == currActivity) {
            App.currentActivity = null
        }
    }

}