package com.example.weather.fragments.alerts.view


import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.MyApplication
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.data.repos.LocalRepo
import com.example.weather.databinding.FragmentAlertsBinding
import com.example.weather.fragments.alerts.viewmodel.AlertViewModel
import com.example.weather.fragments.alerts.viewmodel.AlertViewModelFactory
import com.example.weather.main.view.MainActivity
import com.example.weather.model.AlertData
import com.example.weather.reciver.MyReceiver
import kotlinx.coroutines.InternalCoroutinesApi


class AlertsFragment :Fragment()
{

    val TAG="main"

    lateinit var alertAdapter:AlertAdapter
    lateinit var viewMode: AlertViewModel
   // private val model: AlertViewModel by activityViewModels()
 //  private val model: AlertViewModel by viewModels()

    lateinit var binding: FragmentAlertsBinding
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentAlertsBinding.inflate(layoutInflater)
        val app = (requireActivity().application as MyApplication).activiyComponent
        //Dagger
        viewMode= ViewModelProvider(this, AlertViewModelFactory(app.getLocalRepo())).get(AlertViewModel::class.java)

       // defaultSetting()

      //  registerReceiver


      /*  val intet :IntentFilter = IntentFilter()


        requireActivity().registerReceiver(MyReceiver(),intet)
        requireActivity().unregisterReceiver(MyReceiver())*/


        alertAdapter= AlertAdapter(mutableListOf()) {
            viewMode.cancleAlarm(it)
        }
        binding= FragmentAlertsBinding.inflate(layoutInflater)



        viewMode.getAlertsFromRoom()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {



        viewMode.alertsLiveData.observe(viewLifecycleOwner, {
            alertAdapter.list=it
            alertAdapter.notifyDataSetChanged()
        })

        binding.Alertrecycl.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=alertAdapter
        }
        binding.floatingActionButton.setOnClickListener {

            AddAlertDF(onAddAlert = {
                viewMode.addAlarm(it)
            }).show(parentFragmentManager,"details")
        }
        return binding.root
    }
    companion object {
    }

}