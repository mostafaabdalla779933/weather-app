package com.weathery.weather.data.repos


import com.weathery.weather.data.remote.retrofit.WeatherApiService
import com.weathery.weather.data.remote.retrofit.WeatherService
import com.weathery.weather.model.DataResponse
import retrofit2.Response


class RemoteRepo(val weatherApiService: WeatherApiService ): IRemoteRepo {

    //private val weatherApiService: WeatherApiService = WeatherService.weatherApiService()

    override suspend fun getWeatherFormApi(url:String): Response<DataResponse> {
        return this.weatherApiService.load(url)
    }
}