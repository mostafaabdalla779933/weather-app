package com.example.weather.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.remote.retrofit.WeatherRepoInterface

class MainViewModelFactory(var weatherRepoInterface: WeatherRepoInterface): ViewModelProvider.Factory {




    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return MainViewModel.getInstance(weatherRepoInterface) as T
    }


}