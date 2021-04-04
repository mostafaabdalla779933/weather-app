package com.example.weather.fragments.setting.viewmodel

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.data.remote.location.LocationRepoInterface
import com.example.weather.data.repos.LocalRepo
import com.example.weather.model.Setting
import java.util.*


class SettingViewModel(var locationRepoInterface: LocationRepoInterface): ViewModel(){
    val TAG="main"

    val repo=LocalRepo

    fun getLocation(){

        locationRepoInterface.getLoction()
    }


    fun setLocale(lang:String,contect:Context,activity:Activity) {
        val resources: Resources = contect.getResources()
        val locale = Locale(lang)
        Locale.setDefault(locale);
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        activity?.finish()
        activity?.startActivity(activity?.getIntent())
        Sharedprefer.putRepo(Setting.RETROFIT)
        //(activity as MainActivity).recreate()
    }



    fun putlanguge(language: String){

        repo.putlanguge(language)
    }

    fun getlanguge(): String{

        return repo.getlanguge()
    }

    fun putNotification(notificationFlag: Boolean){

        repo.putNotification(notificationFlag)
    }

    fun getNotification(): Boolean{

        return repo.getNotification()
    }


    fun putTemperUnit(temperUnit: String){

        repo.putTemperUnit(temperUnit)
    }
    fun getTemperUnit(): String{

        return repo.getTemperUnit()
    }
    fun putWindSpeed(WindSpeed: String){

        repo.putWindSpeed(WindSpeed)
    }

    fun getWindSpeed(): String{

        return repo.getWindSpeed()
    }


    fun getLat():Float{

        return repo.getLat()
    }
    fun putLat(lat:Float){

        repo.putLat(lat)
    }
    fun getLng():Float{

        return repo.getLng()
    }

    fun putLng(lng:Float){

        repo.putLng(lng)
    }

    fun putTimeZone(timeZone:String){

        repo.putTimeZone(timeZone)
    }
    fun getTimeZone():String{

        return repo.getTimeZone()
    }


    fun putRepo(rep:String){

        repo.putRepo(rep)
    }

    fun getRepo():String?{

        return Sharedprefer.getRepo()
    }


    fun putRepeating(day:Int){

        repo.putRepeating(day)
    }
    fun getRepeating():Int?{

        return repo.getRepeating()
    }

    fun putSwitch(flag:Boolean){

        repo.putSwitch(flag)
    }
    fun getSwitch():Boolean{

        return repo.getSwitch()
    }

}