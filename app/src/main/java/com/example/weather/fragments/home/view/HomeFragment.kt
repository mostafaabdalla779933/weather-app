package com.example.weather.fragments.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.weather.MyApplication
import com.example.weather.R
import com.example.weather.data.repos.LocalRepo
import com.example.weather.data.repos.RemoteRepo
import com.example.weather.databinding.FragmentHomeBinding
import com.example.weather.main.view.MainActivity
import com.example.weather.main.viewmodel.MainViewModel
import com.example.weather.main.viewmodel.MainViewModelFactory
import com.example.weather.model.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    val TAG="main"
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainViewModel
    lateinit var dailyAdapter: DailyAdapter
    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var pager: ViewPager
    lateinit var unitTemp:String
    lateinit var unitWind:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        //Dagger
       viewModel = ViewModelProvider(requireActivity(),MainViewModelFactory((requireActivity().application as MyApplication).activiyComponent.getRemoteRepo(),(requireActivity().application as MyApplication).activiyComponent.getLocalRepo())).get(MainViewModel::class.java)


        unitTemp=viewModel.getTemperUnit()
        unitWind=viewModel.getWindSpeed()

        dailyAdapter= DailyAdapter(mutableListOf(),unitTemp)
        hourlyAdapter= HourlyAdapter(mutableListOf(),unitTemp)
        // fetch data
        viewModel.getWeather()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        pager=container as ViewPager
        binding.card.visibility=View.INVISIBLE
        binding.firstTime.visibility=View.GONE
        
        
        viewModel.firstTimeLiveData.observe(viewLifecycleOwner, Observer {

            if (it) {
                binding.firstTime.visibility=View.VISIBLE
                binding.progressBar.visibility=View.INVISIBLE
                viewModel.firstComplete()
            }
        })


        binding.firstTime.setOnClickListener(View.OnClickListener {
            pager.currentItem=3
        })


        binding.recyclDaily.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=dailyAdapter
        }

        binding.recyclHourly.apply {
            layoutManager=LinearLayoutManager(activity ,LinearLayoutManager.HORIZONTAL,false)
            adapter=hourlyAdapter
        }


        ///show weather
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {

            binding.progressBar.visibility=View.INVISIBLE
            applyDatatoScreen(it)
        })

        viewModel.erroLive.observe(viewLifecycleOwner, Observer {

            if (it){
                Snackbar.make(this.requireView(), getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE )
                    .setAction(getString(R.string.hide)) { }.show()
                binding.progressBar.visibility=View.INVISIBLE
                viewModel.errorComplete()
            }
        })

        return binding.root
    }

    companion object {

    }

    fun applyDatatoScreen(it:DataResponse){

        binding.apply {

            card.visibility=View.VISIBLE


            txtCoder.text=it?.timezone.split("/")[1]
            cloud.text=it?.current?.clouds?.toString()
            visibility.text=it?.current?.visibility?.toString()
            ultra.text=it?.current?.uvi?.toString()
            pressure.text=it?.current?.pressure?.toString()
            when(unitWind){
                WindSpeed.MPERSEC->wind.text= it?.current?.windSpeed?.toString()+"m/sec"
                WindSpeed.MPERHOUR->wind.text=String.format("%.1f", (it?.current?.windSpeed!! * 2.23)) + "mile/h"
            }
            humidity.text=it?.current?.humidity?.toString()
            when(unitTemp){
                TemperUnit.KELVIN->temp.text=""+(it?.current?.temp?.toInt())+"°K"
                TemperUnit.CELSIUS->temp.text=""+(it?.current?.temp?.toInt())?.toCelsius()+"°C"
                TemperUnit.FAHRENHEIT->temp.text=""+(it?.current?.temp?.toInt())?.toFahrenheit()+"°F"
            }
            txtdescription.text=it?.current?.weather?.get(0)?.description
            windimage.setAnimation(R.raw.windlot)
            visibilityimage.setAnimation(R.raw.visiabilitylot)
        }
        dailyAdapter.list=it.daily?.drop(1)?.take(7)?.toMutableList()!!
        dailyAdapter.notifyDataSetChanged()
        hourlyAdapter.list=it.hourly?.take(24)?.toMutableList()!!
        hourlyAdapter.notifyDataSetChanged()

       // Glide.with(requireActivity().applicationContext).load(setImgLottie(it?.current?.weather?.get(0)?.icon!!)).into(binding.imageicon)
        binding.imageicon.setAnimation(setImgLottie(it?.current?.weather?.get(0)?.icon!!))
    }
}