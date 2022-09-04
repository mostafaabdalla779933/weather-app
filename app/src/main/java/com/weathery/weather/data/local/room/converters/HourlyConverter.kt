package com.weathery.weather.data.local.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weathery.weather.model.HourlyItem

class HourlyConverter {

    @TypeConverter
    public fun toString(list:List<HourlyItem?> ):String{
        return Gson().toJson(list);
    }


    @TypeConverter
    public fun  toArrayList(gson:String ):List<HourlyItem?>{

        val listType = object : TypeToken<List<HourlyItem?>>() {}.type

        return Gson().fromJson<List<HourlyItem?>>(gson,listType);
    }

}