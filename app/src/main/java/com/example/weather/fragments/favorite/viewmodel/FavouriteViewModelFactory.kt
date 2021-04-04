package com.example.weather.fragments.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class FavouriteViewModelFactory: ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouriteViewModel.getInstance() as T
    }


}