package com.weathery.weather.data.repos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.weathery.weather.MyApplication
import com.weathery.weather.data.local.room.RoomDao
import com.weathery.weather.data.local.room.Roomdata
import com.weathery.weather.data.local.sharedpref.ISharedprefer
import com.weathery.weather.data.local.sharedpref.Sharedprefer
import com.weathery.weather.model.AlertData
import com.weathery.weather.model.DataResponse
import com.weathery.weather.model.Favourite
import kotlinx.coroutines.flow.Flow
import kotlin.math.ln

class LocalRepo(val shared: ISharedprefer,val room:RoomDao) : ILocalRepo {

    ///******Data Sources
   // val shared: ISharedprefer =Sharedprefer
   // val room:RoomDao=Roomdata.getDatabase(MyApplication.getContext()).roomDao()


    override suspend fun addWeather(response: DataResponse){

        room.insert(response)
    }


    override suspend fun deleteAllWeather(){

        room.deleteAll()
    }


    override suspend fun getWeatherByTimeZone(timezone: String):DataResponse{

       return room.getWeatherByTimeZone(timezone)
    }


    ////////////////////////////////////////////////////////////////////////////////////

    ///****** favourite table *********//


    override suspend fun addFavourite(fav: Favourite){

        room.addFavourite(fav)
    }

    override suspend fun deleteFavourite(fav: Favourite){

        room.deleteFavourite(fav)
    }

    override suspend fun updateFavourite(fav: Favourite){

        room.updateFavourite(fav)
    }



    override suspend fun getAllFavourite():LiveData<List<Favourite>>{

        return room.getAllFavourite()
    }




    //**********alerts************//

    override suspend fun addAlert(alert: AlertData){

        room.addAlert(alert)
    }

    override suspend fun deleteAlert(alert: AlertData){

        room.deleteAlert(alert)
    }


    override suspend fun getAllAlerts(): Flow<List<AlertData>> {

       return room.getAllAlerts()
    }



    //****************************shared**********************//


    override fun putNotification(notificationFlag: Boolean){

        shared.putNotification(notificationFlag)
    }

    override fun getNotification(): Boolean{

        return shared.getNotification()
    }


    override fun putTemperUnit(temperUnit: String){

        shared.putTemperUnit(temperUnit)
    }
    override fun getTemperUnit(): String{

        return shared.getTemperUnit()
    }
    override fun putWindSpeed(WindSpeed: String){

        shared.putWindSpeed(WindSpeed)
    }

    override fun getWindSpeed(): String{

        return shared.getWindSpeed()
    }


    override fun getLat():Float{

        return shared.getLat()
    }
    override fun putLat(lat:Float){

        shared.putLat(lat)
    }
    override fun getLng():Float{

        return shared.getLng()
    }

    override fun putLng(lng:Float){

        shared.putLng(lng)
    }

    override fun putTimeZone(timeZone:String){

        shared.putTimeZone(timeZone)
    }
    override fun getTimeZone():String{

        return shared.getTimeZone()
    }


    override fun putRepo(repo:String){

        shared.putRepo(repo)
    }

    override fun getRepo():String?{

      return shared.getRepo()
    }


    override fun putRepeating(day:Int){
        shared.putRepeating(day)
    }
    override fun getRepeating():Int?{

       return shared.getRepeating()
    }

    override fun putSwitch(flag:Boolean){

        shared.putSwitch(flag)
    }
    override fun getSwitch():Boolean{

        return shared.getSwitch()
    }
}