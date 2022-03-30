package com.example.weather.data.local.sharedpref

interface ISharedprefer {
    fun putNotification(notificationFlag: Boolean)
    fun getNotification(): Boolean
    fun putTemperUnit(temperUnit: String)
    fun getTemperUnit(): String
    fun putWindSpeed(WindSpeed: String)
    fun getWindSpeed(): String


    fun getLat():Float
    fun putLat(lat:Float)
    fun getLng():Float
    fun putLng(lng:Float)
    fun putTimeZone(timeZone:String)
    fun getTimeZone():String


    fun putRepo(repo:String)
    fun getRepo():String?


    fun putRepeating(day:Int)
    fun getRepeating():Int?

    fun putSwitch(flag:Boolean)
    fun getSwitch():Boolean



}