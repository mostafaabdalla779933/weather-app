package com.example.weather.model

import androidx.room.*
import com.example.weather.data.local.room.converters.AlertConverter
import com.example.weather.data.local.room.converters.CurrentConverter
import com.example.weather.data.local.room.converters.DailyConverter
import com.example.weather.data.local.room.converters.HourlyConverter
import com.google.gson.annotations.SerializedName

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

data class Temp(
	var min: Double? = null,
	var max: Double? = null,
	var eve: Double? = null,
	var night: Double? = null,
	var day: Double? = null,
	var morn: Double? = null
)

data class HourlyItem(
	var temp: Float? = null,
	var visibility: Float? = null,
	var uvi: Double? = null,
	var pressure: Double? = null,
	var clouds: Double? = null,
	var feelsLike: Double? = null,
	var dt: Long? = null,
	var pop: Double? = null,
	var windDeg: Double? = null,
	var dewPoint: Double? = null,
	var weather: List<WeatherItem?>? = null,
	var humidity: Double? = null,
	var windSpeed: Double? = null
)

data class WeatherItem(
	var icon: String? = null,
	var description: String? = null,
	var main: String? = null,
	var id: Double? = null
)

data class DailyItem(
	var sunrise: Int? = null,
	var temp: Temp? = null,
	var uvi: Double? = null,
	var pressure: Double? = null,
	var clouds: Double? = null,
	var feelsLike: FeelsLike? = null,
	var dt: Long? = null,
	var pop: Double? = null,
	var windDeg: Double? = null,
	var dewPoint: Double? = null,
	var sunset: Double? = null,
	var weather: List<WeatherItem?>? = null,
	var humidity: Double? = null,
	var windSpeed: Double? = null
)

data class FeelsLike(
	var eve: Double? = null,
	var night: Double? = null,
	var day: Double? = null,
	var morn: Double? = null
)

data class Current(
	var sunrise: Double? = null,
	var temp: Double? = null,
	var visibility: Double? = null,
	var uvi: Double? = null,
	var pressure: Double? = null,
	var clouds: Double? = null,
	var feelsLike: Double? = null,
	var dt: Double? = null,
	var windDeg: Double? = null,
	var dewPoint: Double? = null,
	var sunset: Double? = null,
	var weather: List<WeatherItem?>? = null,
	var humidity: Double? = null,
	@field:SerializedName("wind_speed")
	var windSpeed: Float? = null
)

data class AlertsItem(

	@field:SerializedName("start")
	val start: Long? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("sender_name")
	val senderName: String? = null,

	@field:SerializedName("end")
	val end: Long? = null,

	@field:SerializedName("event")
	val event: String? = null
)


