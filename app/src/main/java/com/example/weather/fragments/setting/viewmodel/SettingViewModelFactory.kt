package com.example.weather.fragments.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.ILocationRepo

class SettingViewModelFactory (val locationRepo: ILocationRepo,val localRepo: ILocalRepo): ViewModelProvider.Factory{


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return SettingViewModel(locationRepo,localRepo) as T
        }

}