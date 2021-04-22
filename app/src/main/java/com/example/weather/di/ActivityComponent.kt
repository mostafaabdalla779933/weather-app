package com.example.weather.di

import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.IRemoteRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface ActivityComponent {

   fun getRemoteRepo():IRemoteRepo
   fun getLocalRepo():ILocalRepo
}