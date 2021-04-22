package com.example.weather.fragments.alerts.view

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.R
import com.example.weather.databinding.AlertdialogBinding
import com.example.weather.model.AlertsItem
import com.example.weather.model.getDate
import com.google.gson.Gson

class DialogActivity : AppCompatActivity() {

    val TAG = "main"

    lateinit var alertDialog: AlertdialogBinding
    lateinit var ringtone:Ringtone
    lateinit var uri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)


        ringtone = RingtoneManager.getRingtone(applicationContext, uri)
        ringtone.play()

        var intent=intent
        val json=intent.getStringExtra("AlertsItem")

        var alertsItem=Gson().fromJson(json,AlertsItem::class.java)

        var event:String?
        var titl:String?

        if (alertsItem.description==null){
            event="no alerts"
            titl=""
        }else{
            Log.i(TAG, "getChannelNotification: "+alertsItem.description)
            event=alertsItem.event
            titl=alertsItem.description+"\n"+"from:"+alertsItem.start?.getDate()+"\n"+ "to:"+alertsItem?.end?.getDate()
        }

        val builder = AlertDialog.Builder(this)

        builder.setTitle(event)

        builder.setMessage(titl)

        builder.setIcon(R.drawable.cloudy)

        builder.setPositiveButton(R.string.OK) { dialogInterface, which ->
            ringtone.stop()
            finish()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}