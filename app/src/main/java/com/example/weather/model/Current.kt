package com.example.weather.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
):Parcelable
