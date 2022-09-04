package com.weathery.weather.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherService {

        private const val url="https://api.openweathermap.org/"
        fun weatherApiService(): WeatherApiService {
            return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build().create(WeatherApiService::class.java)
        }
}