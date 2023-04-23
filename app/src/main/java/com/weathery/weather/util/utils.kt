package com.weathery.weather.util

import android.content.Context
import android.widget.Toast


fun Context.showMessage(msg:String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}