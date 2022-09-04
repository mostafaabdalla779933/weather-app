package com.weathery.weather

import android.app.Application
import android.content.Context
import com.weathery.weather.di.ActivityComponent
import com.weathery.weather.di.DaggerActivityComponent
import com.weathery.weather.util.locale.LocaleManager

class MyApplication : Application()  {

    companion object{

        lateinit var  instance:Context
        fun getContext():Context= com.weathery.weather.MyApplication.Companion.instance
    }


    lateinit var activiyComponent: ActivityComponent
    override fun onCreate() {
        super.onCreate()

        LocaleManager.init(this)
        com.weathery.weather.MyApplication.Companion.instance =applicationContext
        activiyComponent = DaggerActivityComponent.create()
    }
}