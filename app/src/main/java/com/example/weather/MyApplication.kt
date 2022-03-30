package com.example.weather

import android.app.Application
import android.content.Context
import com.example.weather.di.ActivityComponent
import com.example.weather.di.DaggerActivityComponent
import com.example.weather.util.locale.LocaleManager

class MyApplication : Application()  {

    companion object{

        lateinit var  instance:Context
        fun getContext():Context= instance
    }


    lateinit var activiyComponent: ActivityComponent
    override fun onCreate() {
        super.onCreate()

        LocaleManager.init(this)
        instance=applicationContext
        activiyComponent = DaggerActivityComponent.create()
    }
}