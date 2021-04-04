package com.example.weather.main.viewmodel


import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.MyApplication
import com.example.weather.data.local.room.Roomdata
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.data.remote.retrofit.WeatherRepoInterface
import com.example.weather.data.repos.LocalRepo
import com.example.weather.model.DataResponse
import com.example.weather.model.Setting
import com.example.weather.model.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(var weatherRepoInterface: WeatherRepoInterface): ViewModel() {

    var TAG:String="main"
    var weatherLiveData= MutableLiveData<DataResponse>()
    var firstTimeLiveData=MutableLiveData<Boolean>()
    val repo=LocalRepo
    var loactionLiveData=MutableLiveData<Location>()
    var LoadingLive= MutableLiveData<Boolean>()
    var erroLive= MutableLiveData<Boolean>()

    var gotoHomeLiveData= MutableLiveData<Boolean>()

    companion object{
        var mainViewModel: MainViewModel? =null
        fun getInstance(weatherRepoInterface: WeatherRepoInterface): MainViewModel? {
            if(mainViewModel==null){
                synchronized (MainViewModel::class.java){
                    if(mainViewModel==null){
                        mainViewModel=MainViewModel(weatherRepoInterface)
                    }
                }
            }
            return mainViewModel;
        }
    }




    // fetch data

    fun getWeather(){

        val repoflag=repo.getRepo()

        if(repoflag==null){

           firstTimeLiveData.postValue(true)

        }else if(repoflag==Setting.ROOM){
            //room
            getWeatherFromRoom(repo.getTimeZone())
            Log.i(TAG, "room ")

        }else if(repoflag==Setting.RETROFIT){

            //retrofit

            getWeatherFromRetrofit(repo.getLat().toString(),repo.getLng().toString())

            Log.i(TAG, "retrofit ")
        }

    }

    //***************home Fragment*****************//

    ///get weather from retrofit

    fun getWeatherFromRetrofit(lat:String, lon:String){

        val lang=repo.getlanguge()
        CoroutineScope(Dispatchers.IO).launch {

            if (isOnline(MyApplication.getContext())) {
                val response =
                    weatherRepoInterface.getWeatherFormApi("data/2.5/onecall?lat=$lat&lon=$lon&exclude=alerts,minutely&lang=$lang&appid=4b296deb770fc941bfd35a28581dc8b7")

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {

                        repo.putTimeZone(response.body()?.timezone!!)
                        response.body()?.time = System.currentTimeMillis()
                        setWeatherInRoom(response.body()!!)
                        weatherLiveData.postValue(response.body())

                    } else {
                        erroLive.postValue(true)
                    }
                }

            }else{

                erroLive.postValue(true)

            }
        }
    }

    ///set weather in room
    fun setWeatherInRoom(response: DataResponse){
        CoroutineScope(Dispatchers.IO).launch {

           repo.addWeather(response)
            repo.putRepo(Setting.ROOM)
        }

    }


    fun getWeatherFromRoom(timezone:String){

        CoroutineScope(Dispatchers.IO).launch {



            val response=repo.getWeatherByTimeZone(timezone)

            if (response.time-System.currentTimeMillis()>86400000){


                repo.putRepo(Setting.RETROFIT)
                getWeather()

            }else{

                withContext(Dispatchers.Main){
                    weatherLiveData.postValue(response)
                }

            }
        }
    }





    ///**********************shared******************///

    fun setLocation(location: Location){
        repo.putLat(location.latitude.toFloat())
        repo.putLng(location.longitude.toFloat())
        repo.putRepo(Setting.RETROFIT)
        getWeather()
    }

    fun setLocationFromMap(lat: Float,lon: Float){
        repo.putLat(lat)
        repo.putLng(lon)
        repo.putRepo(Setting.RETROFIT)

        Log.i(TAG, "setLocationFromMap: "+Sharedprefer.toString())
        getWeather()
    }


    fun getTemperUnit():String{

        return repo.getTemperUnit()
    }

    fun getWindSpeed():String{

       return repo.getWindSpeed()
   }



    fun errorComplete(){
        erroLive.postValue(false)
    }


    fun firstComplete(){

        firstTimeLiveData.postValue(false)

    }

}