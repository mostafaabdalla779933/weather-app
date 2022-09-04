package com.weathery.weather.data.local.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.weathery.weather.model.Current

class CurrentConverter {

    @TypeConverter
    public fun toString(current: Current? ):String?{
        return Gson().toJson(current)
    }


    @TypeConverter
    public fun  toArrayList(gson:String ):Current?{

        return Gson().fromJson(gson,Current::class.java);
    }
}