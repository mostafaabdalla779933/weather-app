package com.example.weather.fragmentsdatetime

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.R
import java.util.*

class DateFragment: DialogFragment() {



    lateinit var listener: DatePickerDialog.OnDateSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c: Calendar = Calendar.getInstance()
        val year = c.get(Calendar.YEAR);
        val month = c.get(Calendar.MONTH);
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog =DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)

        return datePickerDialog
    }

}