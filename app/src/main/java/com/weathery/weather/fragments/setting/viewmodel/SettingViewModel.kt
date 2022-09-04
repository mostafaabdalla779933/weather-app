package com.weathery.weather.fragments.setting.viewmodel

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.weathery.weather.data.repos.ILocalRepo
import com.weathery.weather.data.repos.ILocationRepo
import com.weathery.weather.model.Setting
import java.util.*


class SettingViewModel(val locationRepoInterface: ILocationRepo,val repo:ILocalRepo): ViewModel(){

    fun getLocation()=locationRepoInterface.getLoction()


    fun setLocale(lang:String, context:Context, activity:Activity) {
        val resources: Resources = context.resources
        val locale = Locale(lang)
        Locale.setDefault(locale);
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        activity.recreate()
//        activity.finish()
//        activity.startActivity(activity.intent)
        putRepo(Setting.RETROFIT)
        //(activity as MainActivity).recreate()
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

    fun putRepo(rep:String){

        repo.putRepo(rep)
    }



}