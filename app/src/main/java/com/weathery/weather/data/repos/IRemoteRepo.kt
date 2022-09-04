package com.weathery.weather.data.repos

import com.weathery.weather.model.DataResponse
import retrofit2.Response

interface IRemoteRepo {


    suspend fun getWeatherFormApi(url :String): Response<DataResponse>
}