package com.example.weather.data.remote.location

import android.location.Location

interface GetLocation {

        fun onLoctionResult(location: Location)

}