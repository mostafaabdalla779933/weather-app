package com.example.weather.fragments.favorite.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.MyApplication
import com.example.weather.data.local.room.Roomdata
import com.example.weather.data.remote.retrofit.WeatherRepo
import com.example.weather.data.remote.retrofit.WeatherRepoInterface
import com.example.weather.main.viewmodel.MainViewModel
import com.example.weather.model.DataResponse
import com.example.weather.model.Favourite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel: ViewModel() {

        var TAG:String="main"
        var weatherLiveData= MutableLiveData<DataResponse>()
        var favouriteLiveData= MutableLiveData<MutableList<Favourite>>()


        val roomDao= Roomdata.getDatabase(MyApplication.getContext()).roomDao()

         val weatherRepo:WeatherRepo= WeatherRepo()

        var loactionLiveData= MutableLiveData<Location>()
        var LoadingLive= MutableLiveData<Boolean>()
        var erroLive= MutableLiveData<Boolean>()

        var gotoHomeLiveData= MutableLiveData<Boolean>()



        companion object{


            var mainViewModel:FavouriteViewModel? =null

            fun getInstance():FavouriteViewModel? {
                if(mainViewModel==null){
                    synchronized (FavouriteViewModel::class.java){
                        if(mainViewModel==null){
                            mainViewModel=FavouriteViewModel()
                        }
                    }
                }
                return mainViewModel;
            }
        }

    fun addFvaouriteToRoom(favourite:Favourite,context: Context){

        CoroutineScope(Dispatchers.IO).launch {

           val respons=weatherRepo.getWeatherFormApi("data/2.5/onecall?lat=${favourite.lat}&lon=${favourite.lng}&exclude=daily,hourly,alerts,minutely&lang=en&appid=4b296deb770fc941bfd35a28581dc8b7")

            if (respons.isSuccessful){

                favourite.current=respons.body()?.current
                favourite.time=System.currentTimeMillis()
            }

            roomDao.addFavourite(favourite)
            val list=favouriteLiveData.value
            list?.add(favourite)
            favouriteLiveData.postValue(list!!)

        }

    }

    fun updateFavourites(){

        CoroutineScope(Dispatchers.IO).launch {

           val list= roomDao.getAllFavourite()

            list.forEach {

                val response=weatherRepo.getWeatherFormApi("data/2.5/onecall?lat=${it.lat}&lon=${it.lng}&exclude=daily,hourly,alerts,minutely&lang=en&appid=4b296deb770fc941bfd35a28581dc8b7")
                if (response.isSuccessful) {
                    it.current = response.body()?.current
                    roomDao.updateFavourite(it)
                }
            }
        }
    }





    ///get Fvaourite ifrom room
    fun getFvaouritesRoom(context: Context){

        CoroutineScope(Dispatchers.IO).launch {

            val list:List<Favourite> = Roomdata.getDatabase(context).roomDao().getAllFavourite()

            if (!list.isNullOrEmpty()){

                if (System.currentTimeMillis() - list.get(0).time!!>86400000){

                    updateFavourites()
                }
            }
            favouriteLiveData.postValue(list.toMutableList())
        }
    }


    ///delete Fvaourite from room
    fun deletFvaouriteRoom(favourite:Favourite,context: Context){

        CoroutineScope(Dispatchers.IO).launch {

            Roomdata.getDatabase(context).roomDao().deleteFavourite(favourite)

            val list=favouriteLiveData.value
            list?.remove(favourite)

            favouriteLiveData.postValue(list!!)

        }
    }


}