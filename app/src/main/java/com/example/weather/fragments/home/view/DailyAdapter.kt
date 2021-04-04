package com.example.weather.fragments.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather.databinding.DailyrowBinding
import com.example.weather.model.*
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(var list:MutableList<DailyItem?>,var unit:String): RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    class ViewHolder(var rowView:DailyrowBinding): RecyclerView.ViewHolder(rowView.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding= DailyrowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.rowView.daytxt .text= SimpleDateFormat("EE").format(list.get(position)?.dt!!*1000)
        holder.rowView.descriptiontxt .text=list.get(position)?.weather?.get(0)?.description

        when(unit){

            TemperUnit.KELVIN->holder.rowView.temoptxtday .text="" +list.get(position)?.temp?.min?.toInt() + "/" +list.get(position)?.temp?.max?.toInt()

            TemperUnit.CELSIUS->holder.rowView.temoptxtday .text="" +list.get(position)?.temp?.min?.toInt()?.toCelsius() + "/" +list.get(position)?.temp?.max?.toInt()?.toCelsius()

            TemperUnit.FAHRENHEIT->holder.rowView.temoptxtday .text="" +list.get(position)?.temp?.min?.toInt()?.toFahrenheit() + "/" +list.get(position)?.temp?.max?.toInt()?.toFahrenheit()
        }

        Glide.with(holder.itemView.context).load(downloadIcon(list.get(position)?.weather?.get(0)?.icon)).into(holder.rowView.imageView2)

    }

    override fun getItemCount(): Int =list.size

}