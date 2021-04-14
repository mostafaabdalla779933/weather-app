package com.example.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weather.data.local.room.converters.CalenderConverter
import java.util.*
@Entity
data class AlertData(

        var type:String,

        @TypeConverters(CalenderConverter::class)
        @PrimaryKey
        var calendar: Calendar
){

    companion object{

        const val TAG="Alert"

    }
}