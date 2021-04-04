package com.example.weather.fragments.alerts.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.databinding.ActivityAddAlertBinding
import com.example.weather.fragments.alerts.viewmodel.AlertViewModel
import com.example.weather.fragments.alerts.viewmodel.AlertViewModelFactory
import com.example.weather.fragmentsdatetime.DateFragment
import com.example.weather.fragmentsdatetime.TimeFragment
import com.example.weather.model.AlertData
import com.example.weather.model.toast
import java.util.*

class AddAlertActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    lateinit var binding:ActivityAddAlertBinding
    lateinit var calendar: Calendar
    lateinit var viewMdel:AlertViewModel

    val TAG="main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.visibility=View.GONE


        viewMdel= ViewModelProvider(this, AlertViewModelFactory()).get(AlertViewModel::class.java)

        calendar= Calendar.getInstance()

        //******spinner********//
        var myadapter = ArrayAdapter.createFromResource(this,R.array.alert_types,R.layout.spinner_item_)

        binding.spinner.adapter=myadapter


        binding.btnDate.setOnClickListener(View.OnClickListener {
            DateFragment(this).show(supportFragmentManager, "date");
        })

        binding.btntime.setOnClickListener(View.OnClickListener {

            TimeFragment(this).show(supportFragmentManager,"time")
        })


        binding.btnsave.setOnClickListener(View.OnClickListener {

            if(binding.txtdate.text.isNullOrEmpty()||binding.txttime.text.isNullOrEmpty()){
                this.toast("fill all data ")
            }else{
                viewMdel.addAlarm(AlertData("alert",calendar))
                finish()
            }

        })

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