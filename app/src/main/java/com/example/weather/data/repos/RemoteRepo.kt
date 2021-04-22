package com.example.weather.data.repos


import com.example.weather.data.remote.retrofit.WeatherApiService
import com.example.weather.data.remote.retrofit.WeatherService
import com.example.weather.model.DataResponse
import retrofit2.Response


class RemoteRepo(val weatherApiService: WeatherApiService ): IRemoteRepo {

    //private val weatherApiService: WeatherApiService = WeatherService.weatherApiService()

    override suspend fun getWeatherFormApi(url:String): Response<DataResponse> {
        return this.weatherApiService.load(url)
    }
}