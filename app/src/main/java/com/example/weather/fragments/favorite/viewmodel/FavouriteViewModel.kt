package com.example.weather.fragments.favorite.viewmodel

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.IRemoteRepo
import com.example.weather.model.Favourite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(val localRepo:ILocalRepo, val remoteRepo: IRemoteRepo): ViewModel() {

    var TAG:String="main"
    var favouriteLiveData= MediatorLiveData<MutableList<Favourite>>()
    var favouriteLiveDataSearch = MutableLiveData<MutableList<Favourite>>()
    private var searchList : List<Favourite> = listOf()

    fun addFvaouriteToRoom(favourite:Favourite,context: Context){

        CoroutineScope(Dispatchers.IO).launch {
           val respons=remoteRepo.getWeatherFormApi("data/2.5/onecall?lat=${favourite.lat}&lon=${favourite.lng}&exclude=daily,hourly,alerts,minutely&lang=en&appid=4b296deb770fc941bfd35a28581dc8b7")
            if (respons.isSuccessful){
                favourite.current=respons.body()?.current
                favourite.time=System.currentTimeMillis()
            }
            localRepo.addFavourite(favourite)
        }
    }

    fun updateFavourites(){

        CoroutineScope(Dispatchers.IO).launch {

           val list= localRepo.getAllFavourite()

            list.value?.forEach {

                val response=remoteRepo.getWeatherFormApi("data/2.5/onecall?lat=${it.lat}&lon=${it.lng}&exclude=daily,hourly,alerts,minutely&lang=en&appid=4b296deb770fc941bfd35a28581dc8b7")
                if (response.isSuccessful) {
                    it.current = response.body()?.current
                    localRepo.updateFavourite(it)
                }
            }
        }
    }


    fun search(txt:String){
       searchList = favouriteLiveData?.value?.filter { e-> e.name.startsWith(txt,true)} ?: listOf()
       favouriteLiveDataSearch.postValue(searchList?.toMutableList())
    }


    ///get Fvaourite ifrom room
    fun  getFvaouritesRoom(){

        CoroutineScope(Dispatchers.Main).launch {

          favouriteLiveData.addSource(localRepo.getAllFavourite(), Observer {

             favouriteLiveData.postValue(it.toMutableList())

            })
        }
    }
    ///delete Fvaourite from room
    fun deletFvaouriteRoom(favourite:Favourite,context: Context){

        CoroutineScope(Dispatchers.IO).launch {

            localRepo.deleteFavourite(favourite)

            val list=favouriteLiveData.value
            list?.remove(favourite)

            favouriteLiveData.postValue(list!!)

        }
    }
}