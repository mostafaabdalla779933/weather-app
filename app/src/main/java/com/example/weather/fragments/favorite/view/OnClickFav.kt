package com.example.weather.fragments.favorite.view

import com.example.weather.model.Favourite

interface OnClickFav {



    fun onItemDelete(favourite: Favourite)
    fun onItemClick(favourite: Favourite)
}