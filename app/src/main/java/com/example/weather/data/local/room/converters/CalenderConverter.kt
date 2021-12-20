package com.example.weather.data.local.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class CalenderConverter {

    @TypeConverter
    public fun toString(calendar: Calendar):String{
        return Gson().toJson(calendar);
    }


    @TypeConverter
    fun  toCalendar(gson:String ):Calendar{
        //val listType = object : TypeToken<List<AlertsItem?>>() {}.type
        return Gson().fromJson(gson,Calendar::class.java);
    }



}