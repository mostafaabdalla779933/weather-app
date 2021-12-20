package com.example.weather

import android.app.Application
import android.content.Context
import com.example.weather.di.ActivityComponent
import com.example.weather.di.DaggerActivityComponent

class MyApplication : Application()  {

    companion object{

        lateinit var  instance:Context
        fun getContext():Context= instance
    }


    lateinit var activiyComponent: ActivityComponent
    override fun onCreate() {
        super.onCreate()
        instance=applicationContext
        activiyComponent = DaggerActivityComponent.create()
    }
}