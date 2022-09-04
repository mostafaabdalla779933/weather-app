package com.weathery.weather.fragments.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.weathery.weather.R
import com.weathery.weather.databinding.ShowDetailsBinding
import com.weathery.weather.model.*

class ShowDetailsFragment: DialogFragment(){

    lateinit var binding:ShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ShowDetailsBinding.inflate(layoutInflater)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        dialog?.apply {
            window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window?.setBackgroundDrawable(ResourcesCompat.getDrawable(resources,R.drawable.trans,null))
            window?.clearFlags(WindowManager.LayoutParams.ANIMATION_CHANGED)
            setCancelable(true)
        }

        arguments?.let {
            applyDataScreen(it.get(CURRENT) as Current)
        }
        return binding.root
    }

    override fun getTheme(): Int {
        super.getTheme()
        return R.style.DialogStyle
    }


    private fun applyDataScreen(it:Current?){

        binding.apply {
            cloud.text=it?.clouds?.toString()
            visibility.text=it?.visibility?.toString()
            ultra.text=it?.uvi?.toString()
            pressure.text=it?.pressure?.toString()
            when(WindSpeed.MPERHOUR){
                WindSpeed.MPERSEC->wind.text= it?.windSpeed?.toString()+"m/sec"
                WindSpeed.MPERHOUR->wind.text=String.format("%.1f", (it?.windSpeed!! * 2.23)) + "mile/h"
            }

            humidity.text=it?.humidity?.toString()
            when(TemperUnit.CELSIUS){
                TemperUnit.KELVIN->temp.text=""+(it?.temp?.toInt())+"°K"
                TemperUnit.CELSIUS->temp.text=""+(it?.temp?.toInt())?.toCelsius()+"°C"
                TemperUnit.FAHRENHEIT->temp.text=""+(it?.temp?.toInt())?.toFahrenheit()+"°F"
            }
            txtdescription.text=it?.weather?.get(0)?.description
        }
    }

    override fun onDestroy() {
        setFragmentResult("show", bundleOf("show" to "mostafa"))
        super.onDestroy()
    }

    companion object{
        const val CURRENT="current"
    }
}