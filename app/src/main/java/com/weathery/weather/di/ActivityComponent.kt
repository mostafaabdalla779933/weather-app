package com.weathery.weather.di

import com.weathery.weather.data.repos.ILocalRepo
import com.weathery.weather.data.repos.IRemoteRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface ActivityComponent {
   fun getRemoteRepo():IRemoteRepo
   fun getLocalRepo():ILocalRepo
}