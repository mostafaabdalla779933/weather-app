package com.weathery.weather.service


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.weathery.weather.MyApplication
import com.weathery.weather.R
import com.weathery.weather.data.repos.ILocalRepo
import com.weathery.weather.data.repos.IRemoteRepo
import com.weathery.weather.fragments.alerts.view.DialogActivity
import com.weathery.weather.main.view.MainActivity
import com.weathery.weather.model.AlertData
import com.weathery.weather.model.AlertsItem
import com.weathery.weather.model.isOnline
import com.weathery.weather.util.NotificationUtil
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyService : JobIntentService() {
    val TAG = "main"

    lateinit var notificationManager: NotificationManager
    lateinit var notificationHelper: NotificationUtil
    lateinit var localRepo: ILocalRepo
    lateinit var remoteRepo: IRemoteRepo

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        localRepo = (application as com.weathery.weather.MyApplication).activiyComponent.getLocalRepo()
        remoteRepo = (application as com.weathery.weather.MyApplication).activiyComponent.getRemoteRepo()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        (application as com.weathery.weather.MyApplication).activiyComponent.getLocalRepo()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            raiseNotification()
        }

        val alertData: AlertData = Gson().fromJson(intent?.getStringExtra(AlertData.TAG), AlertData::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            if (isOnline(com.weathery.weather.MyApplication.getContext())) {

                localRepo.deleteAlert(alertData)

                val response = remoteRepo.getWeatherFormApi("data/2.5/onecall?lat=${localRepo.getLat()}&lon=${localRepo.getLng()}&exclude=daily,current,hourly,minutely&lang=en&appid=4b296deb770fc941bfd35a28581dc8b7")


                if (response.isSuccessful) {
                    val alerts = response.body()?.alerts
                    if (!alerts.isNullOrEmpty()) {
                        val data = alerts.filter { e -> e?.description?.isNotEmpty()!! }.take(1)
                        Log.i(TAG, "onStartCommand: " + data.size)
                        if (localRepo.getNotification()) {
                            showNotification(alertData, data.get(0)!!)
                        } else {
                            toDialogActivity(com.weathery.weather.MyApplication.getContext(), data.get(0)!!)
                        }
                    } else {
                        if (localRepo.getNotification()) {
                            showNotification(alertData, AlertsItem(description = null))
                        } else {
                            toDialogActivity(com.weathery.weather.MyApplication.getContext(), AlertsItem(description = null))
                        }
                    }
                }

            } else {

                localRepo.deleteAlert(alertData)
            }
        }



        stopSelf()

        return Service.START_STICKY
    }

    override fun onHandleWork(intent: Intent) {

    }


    override fun onDestroy() {
        super.onDestroy()


        Log.i(TAG, "onDestroy:MyService ")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun raiseNotification() {
        val pendingIntent: PendingIntent =
                Intent(this, MainActivity::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(this, 0, notificationIntent, 0)
                }
        notificationManager = getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(NotificationChannel("CHANNEL_DEFAULT_IMPORTANCE", "MyService", NotificationManager.IMPORTANCE_HIGH))


        val notification: Notification = NotificationCompat.Builder(this, "CHANNEL_DEFAULT_IMPORTANCE")
                .setContentTitle("weather")
                .setContentText("updating")
                .setSmallIcon(R.drawable.cloudy)
                .setContentIntent(pendingIntent)
                .setTicker("")
                .setAutoCancel(true)
                .build()

        startForeground(44, notification)

    }


    fun toDialogActivity(context: Context, alertsItem: AlertsItem) {
        val intent2 = Intent("android.intent.action.MAIN")
        intent2.setClass(context, DialogActivity::class.java)
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra("AlertsItem", Gson().toJson(alertsItem))
        context.startActivity(intent2)
    }

    fun showNotification(alertData: AlertData, alertsItem: AlertsItem) {
        notificationHelper = NotificationUtil(alertsItem)
        var builder: NotificationCompat.Builder = notificationHelper.getChannelNotification()
        notificationHelper.getManager().notify(alertData.hashCode(), builder.build())
    }
}

