package com.weathery.weather.data.remote.location

import android.location.Location

interface GetLocation {

        fun onLoctionResult(location: Location)

}