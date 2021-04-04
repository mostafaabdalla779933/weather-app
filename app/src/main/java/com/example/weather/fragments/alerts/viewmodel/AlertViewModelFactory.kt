package com.example.weather.fragments.alerts.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.fragments.setting.viewmodel.SettingViewModel

class AlertViewModelFactory (): ViewModelProvider.Factory{


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return AlertViewModel.getInstance() as T
    }
}