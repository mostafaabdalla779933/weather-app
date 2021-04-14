package com.example.weather.fragments.alerts.view

import com.example.weather.model.AlertData

interface OnClickAlert {


    fun onDeletAlert(alertData: AlertData)
    fun onAddAlert(alertData: AlertData)

}