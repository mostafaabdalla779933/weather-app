package com.example.weather.data.remote.retrofit


import com.example.weather.model.DataResponse
import retrofit2.Response


class WeatherRepo(): WeatherRepoInterface {



    private val weatherApiService: WeatherApiService = WeatherService.getWeather()


    companion object{
        var weatherRepo: WeatherRepo? =null

        fun getInstance(): WeatherRepo? {
            if(weatherRepo ==null){
                synchronized (WeatherRepo::class.java){
                    if(weatherRepo ==null){
                        weatherRepo = WeatherRepo()
                    }
                }
            }
            return weatherRepo;
        }


    }



    override suspend fun getWeatherFormApi(url:String): Response<DataResponse> {


        return this.weatherApiService.load(url)
    }


}