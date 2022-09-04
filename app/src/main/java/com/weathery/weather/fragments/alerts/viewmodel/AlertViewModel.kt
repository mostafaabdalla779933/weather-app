package com.weathery.weather.fragments.alerts.viewmodel



import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.weathery.weather.data.repos.ILocalRepo
import com.weathery.weather.model.AlertData
import com.weathery.weather.util.AlarmUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.*


class AlertViewModel(val repo:ILocalRepo): ViewModel()  {

    var alertsLiveData=MediatorLiveData<MutableList<AlertData>>()
   // var sourceLiveData=MediatorLiveData<MutableList<AlertData>>()
    val TAG="main"
    var alarmUtil=AlarmUtil

    @InternalCoroutinesApi
    fun getAlertsFromRoom(){

        val x=CoroutineScope(Dispatchers.IO).launch {
            repo.getAllAlerts().collect {
                withContext(Dispatchers.Main){
                    alertsLiveData.postValue(it.toMutableList())
                }
            }
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