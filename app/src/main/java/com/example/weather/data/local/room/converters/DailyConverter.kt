package com.example.weather.data.local.room.converters

import androidx.room.TypeConverter
import com.example.weather.model.DailyItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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