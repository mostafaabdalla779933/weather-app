package com.example.weather.fragments.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.databinding.HourlyrowBinding
import com.example.weather.model.*
import java.text.SimpleDateFormat
import java.util.*

class
HourlyAdapter(var list:MutableList<HourlyItem?>,var unit:String): RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    class ViewHolder(var rowView:HourlyrowBinding): RecyclerView.ViewHolder(rowView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding= HourlyrowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rowView.hourtxt.text =  SimpleDateFormat("hh a").format(list.get(position)?.dt!!*1000)

        when(unit){
            TemperUnit.KELVIN->holder.rowView.temptxt .text=""+list.get(position)?.temp?.toInt()
            TemperUnit.CELSIUS-> holder.rowView.temptxt .text=""+list.get(position)?.temp?.toInt()?.toCelsius()
            TemperUnit.FAHRENHEIT->holder.rowView.temptxt .text=""+list.get(position)?.temp?.toInt()?.toFahrenheit()
        }

       // Glide.with(holder.itemView.context).load(setImgLottie(list.get(position)?.weather?.get(0)?.icon!!)).into(holder.rowView.imageView3)
        holder.rowView.imageView3.setAnimation(setImgLottie(list.get(position)?.weather?.get(0)?.icon!!))

    }
    override fun getItemCount(): Int =list.size
}