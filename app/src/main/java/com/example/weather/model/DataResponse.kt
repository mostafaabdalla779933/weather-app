package com.example.weather.model

import androidx.room.*
import com.example.weather.data.local.room.converters.AlertConverter
import com.example.weather.data.local.room.converters.CurrentConverter
import com.example.weather.data.local.room.converters.DailyConverter
import com.example.weather.data.local.room.converters.HourlyConverter

@Entity(tableName = "weather_table")
data class DataResponse(

	@TypeConverters(CurrentConverter::class)
	var current: Current? = null,
	@PrimaryKey
	var timezone: String ,
	var timezoneOffset: Double? = null,
	@TypeConverters(DailyConverter::class)
	var daily: List<DailyItem?>? = null,
	var lon: Float,
	@TypeConverters(HourlyConverter::class)
	var hourly: List<HourlyItem?>? = null,
	var lat: Float,
	@TypeConverters(AlertConverter::class)
    var alerts: List<AlertsItem?>? =null,
	var time:Long
)




