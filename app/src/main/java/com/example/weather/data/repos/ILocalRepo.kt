package com.example.weather.data.repos

import androidx.lifecycle.LiveData
import com.example.weather.model.AlertData
import com.example.weather.model.DataResponse
import com.example.weather.model.Favourite
import kotlinx.coroutines.flow.Flow

interface ILocalRepo {
    suspend fun addWeather(response: DataResponse)
    suspend fun deleteAllWeather()
    suspend fun getWeatherByTimeZone(timezone: String): DataResponse
    suspend fun addFavourite(fav: Favourite)
    suspend fun deleteFavourite(fav: Favourite)
    suspend fun updateFavourite(fav: Favourite)
    suspend fun getAllFavourite(): LiveData<List<Favourite>>
    suspend fun addAlert(alert: AlertData)
    suspend fun deleteAlert(alert: AlertData)
    suspend fun getAllAlerts(): Flow<List<AlertData>>
    fun putlanguge(language: String)
    fun getlanguge(): String
    fun putNotification(notificationFlag: Boolean)
    fun getNotification(): Boolean
    fun putTemperUnit(temperUnit: String)
    fun getTemperUnit(): String
    fun putWindSpeed(WindSpeed: String)
    fun getWindSpeed(): String
    fun getLat(): Float
    fun putLat(lat: Float)
    fun getLng(): Float
    fun putLng(lng: Float)
    fun putTimeZone(timeZone: String)
    fun getTimeZone(): String
    fun putRepo(repo: String)
    fun getRepo(): String?
    fun putRepeating(day: Int)
    fun getRepeating(): Int?
    fun putSwitch(flag: Boolean)
    fun getSwitch(): Boolean
}