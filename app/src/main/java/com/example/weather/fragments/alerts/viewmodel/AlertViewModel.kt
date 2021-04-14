package com.example.weather.fragments.alerts.viewmodel


import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.weather.MyApplication
import com.example.weather.data.local.room.Roomdata
import com.example.weather.data.repos.ILocalRepo
import com.example.weather.data.repos.LocalRepo
import com.example.weather.main.viewmodel.MainViewModel
import com.example.weather.model.AlertData
import com.example.weather.util.AlarmUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlertViewModel(val repo:ILocalRepo): ViewModel() {

    var alertsLiveData=MediatorLiveData<MutableList<AlertData>>()
   // var sourceLiveData=MediatorLiveData<MutableList<AlertData>>()
    val TAG="main"
    var alarmUtil=AlarmUtil

    companion object{

    }

    fun getAlertsFromRoom(){

        CoroutineScope(Dispatchers.IO).launch {

            alertsLiveData.addSource( repo.getAllAlerts(), Observer {
                alertsLiveData.postValue( it.toMutableList())
            })
        }
    }

    fun addAlarm(alertData: AlertData){

        alarmUtil.addAlarm(alertData)

        CoroutineScope(Dispatchers.IO).launch {
            repo.addAlert(alertData)
        }

        val list=alertsLiveData.value
        list?.add(alertData)
        alertsLiveData.postValue(list!!)

    }

    fun cancleAlarm(alertData: AlertData){

        alarmUtil.cancelAlarm(alertData)
        CoroutineScope(Dispatchers.IO).launch {

            repo.deleteAlert(alertData)
        }
        val list=alertsLiveData.value
        list?.remove(alertData)
        alertsLiveData.postValue(list!!)

    }

    fun addRepeatingAlarm(days:Int){
        alarmUtil.addRepeatingAlarm(days)
    }

    fun cancleRepeatingAlarm(){

        alarmUtil.cancleRepeatingAlarm()
    }



}