package com.weathery.weather.fragments.setting.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager


import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.weathery.weather.MyApplication
import com.weathery.weather.R
import com.weathery.weather.databinding.FragmentSettingBinding
import com.weathery.weather.data.remote.location.GetLocation
import com.weathery.weather.data.remote.location.LocationHelper
import com.weathery.weather.data.repos.LocationRepo
import com.weathery.weather.fragments.setting.viewmodel.SettingViewModel
import com.weathery.weather.fragments.setting.viewmodel.SettingViewModelFactory
import com.weathery.weather.main.viewmodel.MainViewModel
import com.weathery.weather.main.viewmodel.MainViewModelFactory
import com.weathery.weather.model.Setting
import com.weathery.weather.model.TemperUnit
import com.weathery.weather.model.WindSpeed
import com.weathery.weather.util.locale.LocaleManager


class SettingFragment : Fragment(), GetLocation {

    val TAG = "main"
    lateinit var binding: FragmentSettingBinding
    lateinit var settingViewModel: SettingViewModel
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingBinding.inflate(layoutInflater)
        //Dagger

        val app = (requireActivity().application as com.weathery.weather.MyApplication).activiyComponent
        mainViewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(app.getRemoteRepo(), app.getLocalRepo())).get(MainViewModel::class.java)
        settingViewModel = ViewModelProvider(this, SettingViewModelFactory(LocationRepo(requireActivity(), this), app.getLocalRepo())).get(SettingViewModel::class.java)

    }


    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

       // pager = container as ViewPager
        setDefaulSettings()

        binding.btnmap.setOnClickListener {
            findNavController().navigate(R.id.action_main_fragment_to_maps_fragment, bundleOf("from" to Tag))
        }

        binding.btngps.setOnClickListener {
            settingViewModel.getLocation()
        }

        //******************************chaecked**************************************//

        binding.enable.setOnClickListener {
            settingViewModel.putNotification(true)
        }

        binding.disable.setOnClickListener {
            settingViewModel.putNotification(false)
        }

        //*******
        binding.arabic.setOnClickListener {
            if(LocaleManager.getInstance().getLanguage()== LocaleManager.EN) {
                settingViewModel.putRepo(Setting.RETROFIT)
                LocaleManager.changeAppLang(requireActivity())
            }
        }

        binding.english.setOnClickListener {
            if(LocaleManager.getInstance().getLanguage()== LocaleManager.AR) {
                settingViewModel.putRepo(Setting.RETROFIT)
                LocaleManager.changeAppLang(requireActivity())
            }
        }

        binding.system.setOnClickListener {
            if(!LocaleManager.getInstance().isFollowingSystemLocale()){
                settingViewModel.putRepo(Setting.RETROFIT)
                LocaleManager.changeAppLangToSystem(requireActivity())
            }
        }

        ///***********
        binding.kelvin.setOnClickListener {
            settingViewModel.putTemperUnit(TemperUnit.KELVIN)
        }
        binding.celsius.setOnClickListener {
            settingViewModel.putTemperUnit(TemperUnit.CELSIUS)
        }
        binding.farhrenheit.setOnClickListener {
            settingViewModel.putTemperUnit(TemperUnit.FAHRENHEIT)
        }

        //**************

        binding.mpersec.setOnClickListener {
            settingViewModel.putWindSpeed(WindSpeed.MPERSEC)
        }
        binding.mileperhour.setOnClickListener {
            settingViewModel.putWindSpeed(WindSpeed.MPERHOUR)
        }
        //*****************************************************************************//

        return binding.root
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LocationHelper.PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "onRequestPermissionsResult: ")
                settingViewModel.getLocation()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(TAG, "onActivityResult: " + 159899525)

    }


    private fun setDefaulSettings() {
        binding.apply {

            if (settingViewModel.getNotification()) {
                enable.isChecked = true
            } else {
                disable.isChecked = true
            }
            when (settingViewModel.getTemperUnit()) {
                TemperUnit.CELSIUS -> celsius.isChecked = true
                TemperUnit.FAHRENHEIT -> farhrenheit.isChecked = true
                TemperUnit.KELVIN -> kelvin.isChecked = true
            }

            if(LocaleManager.getInstance().isFollowingSystemLocale()){
                system.isChecked = true
            }else{
                when (LocaleManager.getInstance().getLanguage()) {
                    LocaleManager.EN -> english.isChecked = true
                    LocaleManager.AR -> arabic.isChecked = true
                }
            }

            when (settingViewModel.getWindSpeed()) {

                WindSpeed.MPERSEC -> mpersec.isChecked = true
                WindSpeed.MPERHOUR -> mileperhour.isChecked = true
            }
        }
    }

    companion object {
        val Tag: String = "Setting"
    }

    override fun onLoctionResult(location: Location) {
        mainViewModel.setLocation(location)
    }
}

