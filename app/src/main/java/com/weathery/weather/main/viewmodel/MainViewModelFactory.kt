package com.weathery.weather.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weathery.weather.data.repos.ILocalRepo
import com.weathery.weather.data.repos.IRemoteRepo

class MainViewModelFactory(val remoteRepo: IRemoteRepo,val localRepo: ILocalRepo): ViewModelProvider.Factory {




    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return MainViewModel(remoteRepo,localRepo) as T
    }


}