package com.example.weather.util.locale

import android.content.ComponentCallbacks
import android.content.res.Configuration

internal class LocaleManagerApplicationCallbacks(private val callback: (Configuration) -> Unit) :
    ComponentCallbacks {

    override fun onConfigurationChanged(newConfig: Configuration) {
        callback.invoke(newConfig)
    }

    override fun onLowMemory() {
        // no-op
    }
}
