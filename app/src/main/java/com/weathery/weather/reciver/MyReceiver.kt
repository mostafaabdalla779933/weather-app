 package com.weathery.weather.reciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.weathery.weather.model.AlertData
import com.weathery.weather.service.MyService
import com.weathery.weather.util.AlarmUtil

class MyReceiver : BroadcastReceiver() {
    val TAG="main"
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "onReceive: "+intent.action)
        when(intent.action){

            AlertData.TAG->{
                var json:String=intent.getStringExtra(AlertData.TAG)!!
                val intent = Intent(context, MyService::class.java)
                intent.putExtra(AlertData.TAG,json)
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent);
                }else{
                   context.startService(intent);
                }
            }
            AlarmUtil.REPEAT_TAG-> Log.i(TAG, "onReceive:reapeating ")

            else->Log.i(TAG, "onReceive:nothing ")
        }

    }
}