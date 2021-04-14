package com.example.weather.fragments.setting.viewmodel

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.ILocationRepo
import com.example.weather.data.repos.LocalRepo
import com.example.weather.model.Setting
import java.util.*


class SettingViewModel(val locationRepoInterface: ILocationRepo,val repo:ILocalRepo): ViewModel(){
    val TAG="main"



    fun getLocation()=locationRepoInterface.getLoction()


    fun setLocale(lang:String, context:Context, activity:Activity) {
        val resources: Resources = context.resources
        val locale = Locale(lang)
        Locale.setDefault(locale);
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics);
        activity.finish()
        activity.startActivity(activity.intent)
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


  /*  fun getLat():Float{

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
    }*/

}