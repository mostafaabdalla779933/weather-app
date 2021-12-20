package com.example.weather.di

import com.example.weather.main.view.MainActivity
import dagger.Component


@Component
interface ApplicationComponent {

    fun inject(activity: MainActivity)
}