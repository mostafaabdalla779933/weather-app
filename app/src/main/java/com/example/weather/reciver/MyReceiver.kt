 package com.example.weather.reciver

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.weather.data.local.sharedpref.ISharedprefer
import com.example.weather.model.AlertData
import com.example.weather.service.MyService
import com.example.weather.util.AlarmUtil
import com.example.weather.util.NotificationUtil

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