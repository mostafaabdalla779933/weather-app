package com.weathery.weather.model

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
