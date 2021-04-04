package com.example.weather.fragments.alerts.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.MyApplication
import com.example.weather.data.local.room.Roomdata
import com.example.weather.main.viewmodel.MainViewModel
import com.example.weather.model.AlertData
import com.example.weather.util.AlarmUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlertViewModel(): ViewModel() {



    var alertsLiveData=MutableLiveData<MutableList<AlertData>>()

    val TAG="main"
    var alarmUtil:AlarmUtil

    init {

        alarmUtil=AlarmUtil

    }


    companion object{


       private var viewModel: AlertViewModel? =null

        fun getInstance(): AlertViewModel? {
            if(viewModel==null){
                synchronized (MainViewModel::class.java){
                    if(viewModel==null){
                        viewModel= AlertViewModel()
                    }
                }
            }
            return viewModel;
        }
    }


    fun fromViewModel(){

        Log.i(TAG, "fromViewModel: ")

    }

    fun getAlertsFromRoom(){

        CoroutineScope(Dispatchers.IO).launch {

           alertsLiveData.postValue( Roomdata.getDatabase(MyApplication.getContext()).roomDao().getAllAlerts().toMutableList())
        }



    }


    //

    fun addAlarm(alertData: AlertData){

        alarmUtil.addAlarm(alertData)

        setAlerttoRoom(alertData)

        val list=alertsLiveData.value
        list?.add(alertData)
        alertsLiveData.postValue(list!!)

    }

    fun cancleAlarm(alertData: AlertData){

        alarmUtil.cancelAlarm(alertData)
        deleteAlertFromRoom(alertData)
        val list=alertsLiveData.value
        list?.remove(alertData)
        alertsLiveData.postValue(list!!)

    }


    fun setAlerttoRoom(alertData: AlertData){

        CoroutineScope(Dispatchers.IO).launch {

            Roomdata.getDatabase(MyApplication.getContext()).roomDao().addAlert(alertData)
        }
    }


    fun deleteAlertFromRoom(alertData: AlertData){
        CoroutineScope(Dispatchers.IO).launch {

            Roomdata.getDatabase(MyApplication.getContext()).roomDao().deleteAlert(alertData)
        }
    }


    fun addRepeatingAlarm(days:Int){
        alarmUtil.addRepeatingAlarm(days)
    }

    fun cancleRepeatingAlarm(){

        alarmUtil.cancleRepeatingAlarm()
    }



}