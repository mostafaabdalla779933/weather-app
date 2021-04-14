package com.example.weather.fragments.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.IRemoteRepo


class FavouriteViewModelFactory(val localRepo: ILocalRepo, val weatherRepo: IRemoteRepo): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouriteViewModel(localRepo,weatherRepo) as T
    }


}