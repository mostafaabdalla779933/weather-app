package com.example.weather.fragments.alerts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.data.repos.LocalRepo
import com.example.weather.databinding.FragmentAlertsBinding
import com.example.weather.fragments.alerts.viewmodel.AlertViewModel
import com.example.weather.fragments.alerts.viewmodel.AlertViewModelFactory
import com.example.weather.model.AlertData



class AlertsFragment :Fragment(),OnClickAlert {

    val TAG="main"

    lateinit var alertAdapter:AlertAdapter
    lateinit var viewMode: AlertViewModel

    lateinit var binding: FragmentAlertsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentAlertsBinding.inflate(layoutInflater)
        viewMode= ViewModelProvider(this, AlertViewModelFactory(LocalRepo)).get(AlertViewModel::class.java)

       // defaultSetting()

        alertAdapter= AlertAdapter(mutableListOf(),this)
        binding= FragmentAlertsBinding.inflate(layoutInflater)


        if (Sharedprefer.getSwitch()){

            binding.switch1.isChecked=true
            binding.alarm24.isEnabled = true
            binding.alarm48.isEnabled = true
            binding.alarm72.isEnabled = true

        }else{
            binding.alarm24.isEnabled = false
            binding.alarm48.isEnabled = false
            binding.alarm72.isEnabled = false
            binding.switch1.isChecked=false

        }

        when(Sharedprefer.getRepeating()){
            1->binding.alarm24.isChecked=true

            2->binding.alarm48.isChecked=true

            3->binding.alarm72.isChecked=true
        }

        viewMode.getAlertsFromRoom()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{




        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->

            if(isChecked){
                binding.alarm24.isEnabled=true
                binding.alarm48.isEnabled=true
                binding.alarm72.isEnabled=true
                Sharedprefer.putSwitch(true)
            }else{
                binding.alarm24.isEnabled=false
                binding.alarm48.isEnabled=false
                binding.alarm72.isEnabled=false
                Sharedprefer.putSwitch(false)
                viewMode.cancleRepeatingAlarm()
            }
        }


        binding.alarm24.setOnClickListener(View.OnClickListener {

            viewMode.addRepeatingAlarm(1)
            Sharedprefer.putRepeating(1)
        })


        binding.alarm48.setOnClickListener(View.OnClickListener {

            viewMode.addRepeatingAlarm(2)
            Sharedprefer.putRepeating(2)
        })

        binding.alarm72.setOnClickListener(View.OnClickListener {

            viewMode.addRepeatingAlarm(3)
            Sharedprefer.putRepeating(3)
        })

        viewMode.alertsLiveData.observe(viewLifecycleOwner, Observer {
            alertAdapter.list=it
            alertAdapter.notifyDataSetChanged()
        })

        binding.Alertrecycl.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=alertAdapter
        }
        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
            AddAlertDF(this).show(parentFragmentManager,"details")
        })
        return binding.root
    }
    companion object {
    }

    override fun onDeletAlert(alertData: AlertData) {
        viewMode.cancleAlarm(alertData)
    }

    override fun onAddAlert(alertData: AlertData) {

        viewMode.addAlarm(alertData)

    }
}