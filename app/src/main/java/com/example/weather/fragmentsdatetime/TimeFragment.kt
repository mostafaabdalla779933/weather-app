package com.example.weather.fragmentsdatetime

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class TimeFragment:DialogFragment(){

    lateinit var listener: TimePickerDialog.OnTimeSetListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity, listener, hour, minute, DateFormat.is24HourFormat(activity));
    }
}