package com.weathery.weather.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.weathery.weather.MyApplication
import com.weathery.weather.model.AlertData
import com.weathery.weather.reciver.MyReceiver
import com.google.gson.Gson
import java.util.*

object AlarmUtil {
    var TAG="main";

    const val REPEAT_TAG="Repeating"

    lateinit var intent: Intent
    var alarmManager: AlarmManager= com.weathery.weather.MyApplication.getContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    lateinit var pendingIntent: PendingIntent


     fun addAlarm(alertData: AlertData){

        intent=Intent(com.weathery.weather.MyApplication.getContext(), MyReceiver::class.java)

        intent.action=AlertData.TAG
        intent.putExtra(AlertData.TAG, Gson().toJson(alertData))

        Log.i(TAG, "addAlarm: "+alertData.hashCode())
        pendingIntent= PendingIntent.getBroadcast(com.weathery.weather.MyApplication.getContext(),alertData.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT);

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

           alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alertData.calendar?.getTimeInMillis()!!,pendingIntent)

            // alarmManager.set(AlarmManager.RTC_WAKEUP, alertData.calendar?.getTimeInMillis()!!,pendingIntent)
        }else{

            alarmManager.set(AlarmManager.RTC_WAKEUP,alertData.calendar?.getTimeInMillis()!!,pendingIntent);
        }


    }

    fun cancelAlarm(alertData: AlertData){

        intent=Intent(com.weathery.weather.MyApplication.getContext(),  MyReceiver::class.java)
        Log.i(TAG, "cancelAlarm: ")
        pendingIntent= PendingIntent.getBroadcast(com.weathery.weather.MyApplication.getContext(),alertData.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)

        pendingIntent.cancel()

    }




    fun addRepeatingAlarm(days:Int){

        intent=Intent(com.weathery.weather.MyApplication.getContext(),  MyReceiver::class.java)
        intent.action="Repeating"//AlertData.TAG

        pendingIntent= PendingIntent.getBroadcast(com.weathery.weather.MyApplication.getContext(),44,intent,0)
        alarmManager= com.weathery.weather.MyApplication.getContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            //set(Calendar.HOUR_OF_DAY, 14)
        }

        alarmManager.setInexactRepeating (AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY*days, pendingIntent)
    }


    fun cancleRepeatingAlarm(){

        intent=Intent(com.weathery.weather.MyApplication.getContext(),  MyReceiver::class.java)

        alarmManager= com.weathery.weather.MyApplication.getContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        pendingIntent= PendingIntent.getBroadcast(com.weathery.weather.MyApplication.getContext(),44,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent)
    }



}