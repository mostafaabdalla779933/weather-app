package com.example.weather.data.repos


import com.example.weather.data.remote.retrofit.WeatherApiService
import com.example.weather.data.remote.retrofit.WeatherService
import com.example.weather.model.DataResponse
import retrofit2.Response


object RemoteRepo: IRemoteRepo {

    private val weatherApiService: WeatherApiService = WeatherService.getWeather()

    override suspend fun getWeatherFormApi(url:String): Response<DataResponse> {
        return this.weatherApiService.load(url)
    }
}