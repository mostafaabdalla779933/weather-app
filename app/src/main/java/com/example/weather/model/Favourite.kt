package com.example.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weather.data.local.room.converters.CurrentConverter

@Entity(tableName = "favorite")
class Favourite (

        @PrimaryKey
        var name:String,
        var lng:Double?,
        var lat:Double?,
        @TypeConverters(CurrentConverter::class)
        var current: Current?=null,
        var time:Long?=null)