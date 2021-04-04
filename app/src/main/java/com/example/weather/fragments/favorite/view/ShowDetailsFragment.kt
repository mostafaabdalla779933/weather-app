package com.example.weather.fragments.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.databinding.ShowDetailsBinding
import com.example.weather.model.*
import java.text.SimpleDateFormat
import java.util.*

class ShowDetailsFragment(var current: Current?): DialogFragment(){

    lateinit var binding:ShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ShowDetailsBinding.inflate(layoutInflater)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        applyDatatoScreen(current)

        return binding.root
    }


    fun applyDatatoScreen(it:Current?){

        binding.apply {

            cloud.text=it?.clouds?.toString()
            visibility.text=it?.visibility?.toString()
            ultra.text=it?.uvi?.toString()
            pressure.text=it?.pressure?.toString()
            when(Sharedprefer.getWindSpeed()){
                WindSpeed.MPERSEC->wind.text= it?.windSpeed?.toString()+"m/sec"
                WindSpeed.MPERHOUR->wind.text=String.format("%.1f", (it?.windSpeed!! * 2.23)) + "mile/h"
            }

            humidity.text=it?.humidity?.toString()
            when(Sharedprefer.getTemperUnit()){
                TemperUnit.KELVIN->temp.text=""+(it?.temp?.toInt())+"°K"
                TemperUnit.CELSIUS->temp.text=""+(it?.temp?.toInt())?.toCelsius()+"°C"
                TemperUnit.FAHRENHEIT->temp.text=""+(it?.temp?.toInt())?.toFahrenheit()+"°F"
            }
            txtdescription.text=it?.weather?.get(0)?.description
        }
        Glide.with(requireActivity().applicationContext).load(downloadIcon(it?.weather?.get(0)?.icon)).into(binding.imageicon)


    }
}