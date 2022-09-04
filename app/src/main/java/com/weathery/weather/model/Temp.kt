package com.weathery.weather.model

data class Temp(
        var min: Double? = null,
        var max: Double? = null,
        var eve: Double? = null,
        var night: Double? = null,
        var day: Double? = null,
        var morn: Double? = null
)