package com.example.weather.data.remote.retrofit


import com.example.weather.model.DataResponse
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Url

interface WeatherApiService {

    @GET
    suspend fun load(@Url url:String):Response<DataResponse>


}