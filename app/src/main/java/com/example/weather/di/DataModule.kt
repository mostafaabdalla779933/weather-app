package com.example.weather.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weather.MyApplication
import com.example.weather.data.local.room.RoomDao
import com.example.weather.data.local.room.Roomdata
import com.example.weather.data.local.sharedpref.ISharedprefer
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.data.remote.retrofit.WeatherApiService
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.IRemoteRepo
import com.example.weather.data.repos.LocalRepo
import com.example.weather.data.repos.RemoteRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class DataModule{


    //************local repo****************//

    @Provides
    @Singleton
    fun provideLocalRepo(shared: ISharedprefer,room: RoomDao): ILocalRepo{

        return LocalRepo(shared,room)
    }


    @Provides
    @Singleton
    fun provideRoom(): RoomDao{

        return  Roomdata.getDatabase(MyApplication.getContext()).roomDao()
    }

    @Provides
    @Singleton
    fun provideSharedpreferenceData(sharedPreferences:SharedPreferences,editor: SharedPreferences.Editor): ISharedprefer {

        return  Sharedprefer(sharedPreferences, editor)
    }



    @Provides
    @Singleton
    fun provideSharedPreferencesEditor(sharedPreferences:SharedPreferences):SharedPreferences.Editor {

        return   sharedPreferences.edit()
    }


    @Provides
    @Singleton
    fun provideSharedpreferences(): SharedPreferences {


        return   MyApplication.getContext().getSharedPreferences("weather", Context.MODE_MULTI_PROCESS)
    }

    //****************remote repo***********************************//


    @Provides
    @Singleton
    fun provideRemoteRepo(weatherApiService: WeatherApiService ):IRemoteRepo{

        return  RemoteRepo(weatherApiService)
    }

    @Provides
    @Singleton
    fun providWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, url: String): Retrofit {
        return Retrofit.Builder().baseUrl(url).addConverterFactory(gsonConverterFactory).build()
    }


    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Provides
    @Singleton
    fun provideUrl():String{
        return "https://api.openweathermap.org/"
    }

}
