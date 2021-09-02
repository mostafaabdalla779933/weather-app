package com.example.weather.main.viewmodel


import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.MyApplication
import com.example.weather.data.repos.IRemoteRepo
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.model.DataResponse
import com.example.weather.model.Setting
import com.example.weather.model.isOnline
import kotlinx.coroutines.*


class MainViewModel(val remoteRepo: IRemoteRepo, val localRepo: ILocalRepo) : ViewModel() {

    var TAG: String = "main"
    var weatherLiveData = MutableLiveData<DataResponse>()
    var firstTimeLiveData = MutableLiveData<Boolean>()
    var erroLive = MutableLiveData<Boolean>()


    // fetch data

    fun fetchWeather() {
        val repoflag = localRepo.getRepo()
        if (repoflag == null) {

            firstTimeLiveData.postValue(true)

        } else if (repoflag == Setting.ROOM) {
            //room
            getWeatherFromRoom(localRepo.getTimeZone())
            Log.i(TAG, "room ")

        } else if (repoflag == Setting.RETROFIT) {

            //retrofit
            getWeatherFromRetrofit(localRepo.getLat().toString(), localRepo.getLng().toString())
            Log.i(TAG, "retrofit ")
        }
    }


    fun refresh(){
        localRepo.putRepo(Setting.RETROFIT)
        fetchWeather()
    }

    //***************home Fragment*****************//

    ///get weather from retrofit

    fun getWeatherFromRetrofit(lat: String, lon: String) {

        val lang = localRepo.getlanguge()

       
        CoroutineScope(Dispatchers.IO).launch {

            if (isOnline(MyApplication.getContext())) {
                val response =
                        remoteRepo.getWeatherFormApi("data/2.5/onecall?lat=$lat&lon=$lon&exclude=alerts,minutely&lang=$lang&appid=4b296deb770fc941bfd35a28581dc8b7")

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        localRepo.putTimeZone(response.body()?.timezone!!)
                        response.body()?.time = System.currentTimeMillis()
                        setWeatherInRoom(response.body()!!)
                        weatherLiveData.postValue(response.body())
                    } else {
                        erroLive.postValue(true)
                    }
                }
            } else {
                erroLive.postValue(true)
            }
        }
    }

    ///set weather in room
    fun setWeatherInRoom(response: DataResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            localRepo.addWeather(response)
            localRepo.putRepo(Setting.ROOM)
        }

    }


    fun getWeatherFromRoom(timezone: String) {

        CoroutineScope(Dispatchers.IO).launch {

            val response = localRepo.getWeatherByTimeZone(timezone)

            if (response.time - System.currentTimeMillis() > 86400000) {
                localRepo.putRepo(Setting.RETROFIT)
                fetchWeather()
            } else {
                withContext(Dispatchers.Main) {
                    weatherLiveData.postValue(response)
                }
            }
        }
    }

    ///**********************shared******************///

    fun setLocation(location: Location) {
        localRepo.putLat(location.latitude.toFloat())
        localRepo.putLng(location.longitude.toFloat())
        localRepo.putRepo(Setting.RETROFIT)
        fetchWeather()
    }

    fun setLocationFromMap(lat: Float, lon: Float) {
        localRepo.putLat(lat)
        localRepo.putLng(lon)
        localRepo.putRepo(Setting.RETROFIT)
        fetchWeather()
    }

    fun getTemperUnit(): String {
        return localRepo.getTemperUnit()
    }

    fun getWindSpeed(): String {
        return localRepo.getWindSpeed()
    }

    fun errorComplete() {
        erroLive.postValue(false)
    }

    fun firstComplete() {
        firstTimeLiveData.postValue(false)
    }
}