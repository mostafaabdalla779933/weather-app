package com.example.weather.fragments.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.remote.location.LocationRepoInterface
import com.example.weather.data.remote.retrofit.WeatherRepoInterface
import com.example.weather.main.viewmodel.MainViewModel

class SettingViewModelFactory (var locationRepoInterface: LocationRepoInterface): ViewModelProvider.Factory{


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return SettingViewModel(locationRepoInterface) as T
        }

}