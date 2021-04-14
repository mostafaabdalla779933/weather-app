package com.example.weather.data.repos

import com.example.weather.model.DataResponse
import retrofit2.Response

interface IRemoteRepo {


    suspend fun getWeatherFormApi(url :String): Response<DataResponse>
}