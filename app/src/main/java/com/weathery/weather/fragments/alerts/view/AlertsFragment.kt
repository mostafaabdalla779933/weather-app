package com.weathery.weather.fragments.alerts.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.weathery.weather.databinding.FragmentAlertsBinding
import com.weathery.weather.fragments.alerts.viewmodel.AlertViewModel
import com.weathery.weather.fragments.alerts.viewmodel.AlertViewModelFactory


class AlertsFragment :Fragment()
{

    val TAG="main"

    lateinit var alertAdapter:AlertAdapter
    lateinit var viewMode: AlertViewModel
   // private val model: AlertViewModel by activityViewModels()
 //  private val model: AlertViewModel by viewModels()

    lateinit var binding: FragmentAlertsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentAlertsBinding.inflate(layoutInflater)
        val app = (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent
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



        viewMode.alertsLiveData.observe(viewLifecycleOwner) {
            alertAdapter.list=it
            alertAdapter.notifyDataSetChanged()
        }

        binding.Alertrecycl.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=alertAdapter
        }
        binding.floatingActionButton.setOnClickListener {
            AddAlertDF().also { dialog->
                dialog.onAddAlert={
                    viewMode.addAlarm(it)
                }
                dialog.show(parentFragmentManager,"details")
            }
        }
        return binding.root
    }
    companion object {
    }

}