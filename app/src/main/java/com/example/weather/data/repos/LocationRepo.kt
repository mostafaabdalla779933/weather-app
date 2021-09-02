package com.example.weather.data.repos

import android.app.Activity
import com.example.weather.data.remote.location.GetLocation
import com.example.weather.data.remote.location.LocationHelper

class LocationRepo(activity: Activity, getLocation: GetLocation) : ILocationRepo {

    var locationHelper: LocationHelper
    init {
        locationHelper= LocationHelper(activity, getLocation)
    }

    override fun getLoction() {

       locationHelper.getLastLocation()
    }


}