package com.example.weather.util


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weather.MyApplication
import com.example.weather.R
import com.example.weather.model.AlertsItem
import com.example.weather.model.getDate


class NotificationUtil(var alertsItem: AlertsItem):ContextWrapper(MyApplication.getContext()) {

   val TAG="main"
   val  channelID = "channelID";
   val  channelName = "Channel Name"

    init {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            createChannel()

        }else{

            sendNotification()

        }
    }

    companion object{



    }


        private var mManager: NotificationManager?=null

        @RequiresApi(api = Build.VERSION_CODES.O)
        fun createChannel() {
           val channel= NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

            getManager().createNotificationChannel(channel);
        }

        fun getManager():NotificationManager {
            if (mManager == null) {
                mManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }

            return mManager!!;
        }

        fun getChannelNotification(): NotificationCompat.Builder{

            var event:String?
            var titl:String?
            if (alertsItem.description==null){

                Log.i(TAG, "getChannelNotification: ")
                event="no alerts"
                titl=""
            }else{

                Log.i(TAG, "getChannelNotification: "+alertsItem.description)
                event=alertsItem.event
                titl=alertsItem.description+"\n"+"from:"+alertsItem.start?.getDate()+"\n"+ "to:"+alertsItem?.end?.getDate()
            }

            var sound:Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            return NotificationCompat.Builder(getApplicationContext(), channelID)
            .setContentTitle(event)
                .setContentText(titl)
                .setStyle(NotificationCompat.BigTextStyle())
                .setDefaults(Notification.FLAG_SHOW_LIGHTS)
                .setSubText("")
                .setSmallIcon(R.drawable.cloudy)
                .setAutoCancel(false)
                .setSound(sound)
                .setWhen(System.currentTimeMillis())
                .setLights(0xff00ff00.toInt(), 300, 100)
                .setContentIntent(null)
        }

       fun  sendNotification() {

            var  builder:NotificationCompat.Builder =NotificationCompat.Builder(this)
            builder.setSmallIcon(R.drawable.cloudy)
            builder.setContentIntent(null)
            builder.setContentTitle(alertsItem.event)
            builder.setStyle(NotificationCompat.BigTextStyle())
            builder.setContentText(alertsItem.description+"\n"+alertsItem.start?.getDate()+"\n"+ alertsItem?.end?.getDate());
            builder.setSubText("");
            var notificationManager:NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, builder.build());
        }
}