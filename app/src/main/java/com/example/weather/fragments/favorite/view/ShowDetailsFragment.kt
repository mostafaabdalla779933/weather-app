package com.example.weather.fragments.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.weather.databinding.ShowDetailsBinding
import com.example.weather.model.*

class ShowDetailsFragment(var current: Current?): DialogFragment(){

    lateinit var binding:ShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ShowDetailsBinding.inflate(layoutInflater)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        applyDatatoScreen(current)

        return binding.root
    }


    fun applyDatatoScreen(it:Current?){

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
        Glide.with(requireActivity().applicationContext).load(setImgLottie(it?.weather?.get(0)?.icon!!)).into(binding.imageicon)




    }
}