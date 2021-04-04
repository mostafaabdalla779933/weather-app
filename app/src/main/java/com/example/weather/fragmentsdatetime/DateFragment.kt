package com.example.weather.fragmentsdatetime

import android.app.DatePickerDialog
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

class DateFragment(var listener: DatePickerDialog.OnDateSetListener):DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c: Calendar = Calendar.getInstance()
        var year = c.get(Calendar.YEAR);
        var month = c.get(Calendar.MONTH);
        var day = c.get(Calendar.DAY_OF_MONTH)
        var datePickerDialog =DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)

        return datePickerDialog
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time, container, false)
    }
}