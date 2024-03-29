package com.weathery.weather.fragments.alerts.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.weathery.weather.databinding.AddAlertDialogBinding
import com.weathery.weather.fragmentsdatetime.DateFragment
import com.weathery.weather.fragmentsdatetime.TimeFragment
import com.weathery.weather.model.*
import java.util.*

class AddAlertDF: DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    lateinit var binding:AddAlertDialogBinding
    lateinit var calendar: Calendar
    lateinit var onAddAlert:(AlertData)->Unit
    val TAG="main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= AddAlertDialogBinding.inflate(layoutInflater)

        calendar= Calendar.getInstance()

        binding.btnDate.setOnClickListener(View.OnClickListener {
            DateFragment().also { date->
                date.listener=this
                date.show(parentFragmentManager, "date")
            }
        })

        binding.btntime.setOnClickListener(View.OnClickListener {
            TimeFragment().also { time->
                time.listener=this
                time.show(parentFragmentManager, "date")
            }
        })

        binding.btnsave.setOnClickListener(View.OnClickListener {

            if(binding.txtdate.text.isNullOrEmpty()||binding.txttime.text.isNullOrEmpty()){
                requireActivity().toast("fill all data ")
            }else{
                ///edit
                onAddAlert(AlertData("alert",calendar))
                this.dismiss()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {



        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        binding.txtdate.text=""+ year+"/"+(month+1)+"/"+dayOfMonth
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);

        if(hourOfDay>12){

            binding.txttime.text=""+(hourOfDay-12)+":"+minute+"PM"
        }else{

            binding.txttime.text=""+hourOfDay+":"+minute +"AM"
        }
    }


}