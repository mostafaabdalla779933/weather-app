package com.example.weather.model

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
