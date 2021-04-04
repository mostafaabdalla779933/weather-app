package com.example.weather.data.remote.retrofit

import com.example.weather.model.DataResponse
import retrofit2.Response

interface WeatherRepoInterface {


    suspend fun getWeatherFormApi(url :String): Response<DataResponse>
}