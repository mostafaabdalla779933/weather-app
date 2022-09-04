package com.weathery.weather.data.repos

import android.app.Activity
import com.weathery.weather.data.remote.location.GetLocation
import com.weathery.weather.data.remote.location.LocationHelper

class LocationRepo(activity: Activity, getLocation: GetLocation) : ILocationRepo {

    var locationHelper: LocationHelper = LocationHelper(activity, getLocation)

    override fun getLoction() {

       locationHelper.getLastLocation()
    }


}