package com.weathery.weather.fragments.alerts.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weathery.weather.data.repos.ILocalRepo

class AlertViewModelFactory (val repo: ILocalRepo): ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return AlertViewModel(repo) as T
    }
}