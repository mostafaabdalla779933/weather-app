package com.example.weather.fragments.setting.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources


import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.databinding.FragmentSettingBinding
import com.example.weather.data.remote.location.GetLocation
import com.example.weather.data.remote.location.LocationHelper
import com.example.weather.data.remote.location.LocationRepo
import com.example.weather.data.remote.retrofit.WeatherRepo
import com.example.weather.fragments.setting.viewmodel.SettingViewModel
import com.example.weather.fragments.setting.viewmodel.SettingViewModelFactory
import com.example.weather.main.viewmodel.MainViewModel
import com.example.weather.main.viewmodel.MainViewModelFactory
import com.example.weather.map.MapActivity
import com.example.weather.model.Language
import com.example.weather.model.TemperUnit
import com.example.weather.model.WindSpeed
import java.util.*


class SettingFragment : Fragment(), GetLocation {

    val TAG="main"

    lateinit var binding: FragmentSettingBinding
    lateinit var  settingViewModel: SettingViewModel
    lateinit var mainViewModel: MainViewModel
    lateinit var pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentSettingBinding.inflate(layoutInflater)

        mainViewModel= ViewModelProvider(this,MainViewModelFactory(WeatherRepo.getInstance()!!)).get(MainViewModel::class.java)
        settingViewModel= ViewModelProvider(this,SettingViewModelFactory(LocationRepo(requireActivity(),this))).get(SettingViewModel::class.java)
    }


    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        pager = container as ViewPager
        setDefaulSettings()

        binding.btnmap.setOnClickListener(View.OnClickListener {
            requireActivity().startActivity(Intent(this.context,MapActivity::class.java).putExtra("from",Tag))
        })

        binding.btngps.setOnClickListener(View.OnClickListener {

            settingViewModel.getLocation()

        })

        //******************************chaecked**************************************//

        binding.enable.setOnClickListener(View.OnClickListener {

            settingViewModel.putNotification(true)
        })


        binding.disable.setOnClickListener(View.OnClickListener {

            settingViewModel.putNotification(false)
        })


        //*******
        binding.arabic.setOnClickListener(View.OnClickListener {
            settingViewModel.putlanguge(Language.ARABIC)

            settingViewModel.setLocale("ar",requireContext(),requireActivity())

        })

        binding.english.setOnClickListener(View.OnClickListener {

            settingViewModel.putlanguge(Language.ENGLISH)
            settingViewModel.setLocale("en",requireContext(),requireActivity())

        })

        ///***********
        binding.kelvin.setOnClickListener(View.OnClickListener {

            settingViewModel.putTemperUnit(TemperUnit.KELVIN)
        })
        binding.celsius.setOnClickListener(View.OnClickListener {
            settingViewModel.putTemperUnit(TemperUnit.CELSIUS)
        })
        binding.farhrenheit.setOnClickListener(View.OnClickListener {

            settingViewModel.putTemperUnit(TemperUnit.FAHRENHEIT)
        })

        //**************

        binding.mpersec.setOnClickListener(View.OnClickListener {
            settingViewModel.putWindSpeed(WindSpeed.MPERSEC)
        })
        binding.mileperhour.setOnClickListener(View.OnClickListener {
            settingViewModel.putWindSpeed(WindSpeed.MPERHOUR)

        })
        //*****************************************************************************//



        mainViewModel.gotoHomeLiveData.observe(viewLifecycleOwner, Observer {

            if (it){
                mainViewModel.gotoHomeLiveData.postValue(false)
                pager.currentItem=0
            }
        })

       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== LocationHelper.PERMISSION_ID){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Log.i(TAG, "onRequestPermissionsResult: ")
                settingViewModel.getLocation()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(TAG, "onActivityResult: "+159899525)

    }


    private fun setDefaulSettings() {
        binding.apply {

            if (settingViewModel.getNotification()) {
                enable.isChecked = true
            }else{
                disable.isChecked=true
            }
            when(settingViewModel.getTemperUnit()){
                TemperUnit.CELSIUS->celsius.isChecked=true
                TemperUnit.FAHRENHEIT->farhrenheit.isChecked=true
                TemperUnit.KELVIN->kelvin.isChecked=true
            }
            when(settingViewModel.getlanguge()){
                Language.ENGLISH->english.isChecked=true
                Language.ARABIC->arabic.isChecked=true
            }
            when(settingViewModel.getWindSpeed()){

                WindSpeed.MPERSEC->mpersec.isChecked=true
                WindSpeed.MPERHOUR->mileperhour.isChecked=true
            }
        }
    }

    companion object {
        val Tag:String="Setting"
    }

    override fun onLoctionResult(location: Location) {
        mainViewModel.setLocation(location)
        pager.currentItem=0
    }
}

