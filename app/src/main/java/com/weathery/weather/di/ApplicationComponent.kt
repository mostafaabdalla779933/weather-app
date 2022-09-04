package com.weathery.weather.di

import com.weathery.weather.main.view.MainActivity
import dagger.Component


@Component
interface ApplicationComponent {

    fun inject(activity: MainActivity)
}