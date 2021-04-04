package com.example.weather.data.repos

import androidx.room.*
import com.example.weather.MyApplication
import com.example.weather.data.local.room.Roomdata
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.model.AlertData
import com.example.weather.model.DataResponse
import com.example.weather.model.Favourite
import kotlin.math.ln

object LocalRepo{

    val shared=Sharedprefer

    val room=Roomdata.getDatabase(MyApplication.getContext()).roomDao()





    suspend fun addWeather(response: DataResponse){

        room.insert(response)
    }


    suspend fun deleteAllWeather(){

        room.deleteAll()
    }


    suspend fun getWeatherByTimeZone(timezone: String):DataResponse{

       return room.getWeatherByTimeZone(timezone)
    }


    ////////////////////////////////////////////////////////////////////////////////////

    ///****** favourite table *********//


    suspend fun addFavourite(fav: Favourite){

        room.addFavourite(fav)
    }

    suspend fun deleteFavourite(fav: Favourite){

        room.deleteFavourite(fav)
    }



    suspend fun getAllFavourite():List<Favourite>{

        return room.getAllFavourite()
    }




    //**********alerts************//

    suspend fun addAlert(alert: AlertData){

        room.addAlert(alert)
    }

    suspend fun deleteAlert(alert: AlertData){

        room.deleteAlert(alert)
    }


    suspend fun getAllAlerts():List<AlertData>{

       return room.getAllAlerts()
    }



    //****************************shared**********************//

    fun putlanguge(language: String){

        Sharedprefer.putlanguge(language)
    }

    fun getlanguge(): String{

        return Sharedprefer.getlanguge()
    }

    fun putNotification(notificationFlag: Boolean){

        Sharedprefer.putNotification(notificationFlag)
    }

    fun getNotification(): Boolean{

        return Sharedprefer.getNotification()
    }


    fun putTemperUnit(temperUnit: String){

        Sharedprefer.putTemperUnit(temperUnit)
    }
    fun getTemperUnit(): String{

        return Sharedprefer.getTemperUnit()
    }
    fun putWindSpeed(WindSpeed: String){

        Sharedprefer.putWindSpeed(WindSpeed)
    }

    fun getWindSpeed(): String{

        return Sharedprefer.getWindSpeed()
    }


    fun getLat():Float{

        return Sharedprefer.getLat()
    }
    fun putLat(lat:Float){

        Sharedprefer.putLat(lat)
    }
    fun getLng():Float{

        return Sharedprefer.getLng()
    }

    fun putLng(lng:Float){

        Sharedprefer.putLng(lng)
    }

    fun putTimeZone(timeZone:String){

        Sharedprefer.putTimeZone(timeZone)
    }
    fun getTimeZone():String{

        return Sharedprefer.getTimeZone()
    }


    fun putRepo(repo:String){

        Sharedprefer.putRepo(repo)
    }

    fun getRepo():String?{

      return Sharedprefer.getRepo()
    }


    fun putRepeating(day:Int){

        Sharedprefer.putRepeating(day)
    }
    fun getRepeating():Int?{

       return Sharedprefer.getRepeating()
    }

    fun putSwitch(flag:Boolean){

        Sharedprefer.putSwitch(flag)
    }
    fun getSwitch():Boolean{

        return Sharedprefer.getSwitch()
    }
}