package com.example.weather.data.remote.location

import android.app.Activity
import android.location.Location

class LocationRepo(private var activity: Activity, private var getLocation: GetLocation) : LocationRepoInterface {

     var locationHelper: LocationHelper
    init {
        locationHelper= LocationHelper(activity,getLocation)
    }

    override fun getLoction() {

       locationHelper.getLastLocation()
    }


}