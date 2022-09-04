package com.weathery.weather.data.remote.retrofit


import com.weathery.weather.model.DataResponse
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Url

interface WeatherApiService {

    @GET
    suspend fun load(@Url url:String):Response<DataResponse>
}