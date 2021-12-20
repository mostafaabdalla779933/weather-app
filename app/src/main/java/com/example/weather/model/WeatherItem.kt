package com.example.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherItem(
        var icon: String? = null,
        var description: String? = null,
        var main: String? = null,
        var id: Double? = null
): Parcelable
