package com.example.weather.data.local.room.converters


import androidx.room.TypeConverter
import com.example.weather.model.AlertsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.util.*

class AlertConverter {

    @TypeConverter
    fun toString(list:List<AlertsItem?>? ):String?{
        return Gson().toJson(list);
    }


    @TypeConverter
    fun  toAlets(gson:String? ):List<AlertsItem?>?{
        val listType = object : TypeToken<List<AlertsItem?>>() {}.type
        return Gson().fromJson(gson,listType)
    }

}