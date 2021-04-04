package com.example.weather.data.local.room.converters

import androidx.room.TypeConverter
import com.example.weather.model.Current
import com.google.gson.Gson

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