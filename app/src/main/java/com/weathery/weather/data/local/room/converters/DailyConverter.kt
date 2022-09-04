package com.weathery.weather.data.local.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weathery.weather.model.DailyItem


class DailyConverter {

    @TypeConverter
    public fun toString(list:List<DailyItem?> ):String{
        return Gson().toJson(list);
    }


    @TypeConverter
    public fun  toArrayList(gson:String ):List<DailyItem?>{

        val listType = object : TypeToken<List<DailyItem?>>() {}.type

        return Gson().fromJson<List<DailyItem?>>(gson,listType);
    }

}