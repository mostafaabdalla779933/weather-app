package com.example.weather.data.repos

import android.app.Activity
import com.example.weather.data.remote.location.GetLocation
import com.example.weather.data.remote.location.LocationHelper

class LocationRepo(private var activity: Activity, private var getLocation: GetLocation) : ILocationRepo {

    var locationHelper: LocationHelper
    init {
        locationHelper= LocationHelper(activity, getLocation)
    }

    override fun getLoction() {

       locationHelper.getLastLocation()
    }


}