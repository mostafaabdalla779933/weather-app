package com.example.weather.data.local.sharedpref

import android.content.SharedPreferences
import com.example.weather.model.Language
import com.example.weather.model.Setting
import com.example.weather.model.TemperUnit
import com.example.weather.model.WindSpeed


class Sharedprefer(val sharedPreferences:SharedPreferences,val editor: SharedPreferences.Editor) : ISharedprefer {

   // private val sharedPreferences: SharedPreferences= MyApplication.getContext() .getSharedPreferences("weather", Context.MODE_MULTI_PROCESS)
  // private val editor: SharedPreferences.Editor=sharedPreferences.edit()
    var TAG="main"
    //**************languge***********//
    override fun putlanguge(language: String){
        editor.putString( Setting.LANGUADE ,language)
        editor.apply()
    }

    override fun getlanguge():String{
        return sharedPreferences.getString(Setting.LANGUADE,Language.ENGLISH)!!
    }


    //**************notification***********//

    override fun putNotification(notificationFlag: Boolean){
        editor.putBoolean( Setting.NOTIFICATION ,notificationFlag)
        editor.apply()
    }

    override fun getNotification():Boolean{
        return sharedPreferences.getBoolean(Setting.NOTIFICATION,true)!!
    }
    //************TemperUnit*************//
    override fun putTemperUnit(temperUnit: String){
        editor.putString( Setting.TEMPERATURE ,temperUnit)
        editor.apply()
    }

    override fun getTemperUnit():String{

        return sharedPreferences.getString(Setting.TEMPERATURE,TemperUnit.KELVIN)!!
    }


    //************WindSpeed*************//

    override fun putWindSpeed(WindSpeed: String){

        editor.putString( Setting.WINDSPEED ,WindSpeed)
        editor.apply()

    }

    override fun getWindSpeed():String{

        return sharedPreferences.getString(Setting.WINDSPEED,WindSpeed.MPERSEC)!!
    }

    //*****************current**************//

    override fun putTimeZone(timeZone: String){

        editor.putString( Setting.TIMEZONE ,timeZone)
        editor.apply()

    }

    override fun getTimeZone():String{

        return sharedPreferences.getString(Setting.TIMEZONE,null)!!
    }



    override fun putLng(lng: Float){

        editor.putFloat( Setting.LNG ,lng)
        editor.apply()

    }

    override fun getLat():Float{

        return sharedPreferences.getFloat(Setting.LAT, 0f)
    }

    override fun putLat(lat: Float) {
        editor.putFloat( Setting.LAT ,lat)
        editor.apply()
    }

    override fun getLng(): Float {

        return sharedPreferences.getFloat(Setting.LNG, 0f)
    }



    //***********************status*****************//

    override fun putRepo(repo: String) {
        editor.putString( Setting.REPO ,repo)
        editor.apply()
    }

    override fun getRepo(): String? {

        return sharedPreferences.getString(Setting.REPO, null)
    }



    //*******************repeating Alarm*******************//

    override fun putRepeating(day: Int) {
       editor.putInt(Setting.REPEATING,day)
       editor.apply()
    }

    override fun getRepeating(): Int {

        return  sharedPreferences.getInt(Setting.REPEATING,0)
    }

    //*****************switch****************************//
    override fun putSwitch(flag: Boolean) {
        editor.putBoolean(Setting.SWITCH,flag)
        editor.apply()
    }

    override fun getSwitch(): Boolean {

        return  sharedPreferences.getBoolean(Setting.SWITCH,false)
    }

}