package com.example.weather.fragmentsdatetime

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.weather.R
import java.util.*

class TimeFragment(var listener: TimePickerDialog.OnTimeSetListener):DialogFragment(){

    //lateinit var listener: TimePickerDialog.OnTimeSetListener




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       // return super.onCreateDialog(savedInstanceState)

        val c: Calendar = Calendar.getInstance()

        var hour = c.get(Calendar.HOUR_OF_DAY)

        var minute = c.get(Calendar.MINUTE)

       // listener = activity as(TimePickerDialog.OnTimeSetListener)

        return TimePickerDialog(activity, listener, hour, minute, DateFormat.is24HourFormat(activity));

    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)


        return inflater.inflate(R.layout.fragment_time, container, false)

    }


}