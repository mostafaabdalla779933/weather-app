package com.weathery.weather.data.remote.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.weathery.weather.model.toast
import com.google.android.gms.location.*




class LocationHelper constructor(var activity: Activity,var getLocation: GetLocation) {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object{
        const val PERMISSION_ID=55
        const val TAG="main"
    }

        @SuppressLint("MissingPermission")
        fun getLastLocation() {


            fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(activity)


            if(checkPermissions()){

                if(isLocationEnabled()){

                    fusedLocationProviderClient.lastLocation.addOnSuccessListener(activity) {
                            location ->

                        if (location == null ) {
                            requestNewLocationData() //get fresh location

                        }else{
                            /////use location
                            getLocation.onLoctionResult(location)
                        }
                    }

                }else{
                    activity.applicationContext.toast("Turn your location")
                    val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    activity.applicationContext.startActivity(intent)
                }

            }else{
                requestPermission()
            }
        }



    private fun  checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false

    }

    private fun requestPermission(){

        ActivityCompat.requestPermissions(activity , arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager =activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){  //get fresh location

        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.myLooper()!!)

    }


    private val locationCallBack: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            val location=p0.lastLocation
            getLocation.onLoctionResult(location)
        }
    }
}

