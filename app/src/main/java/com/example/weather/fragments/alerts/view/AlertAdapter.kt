package com.example.weather.fragments.alerts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.AlertRowBinding
import com.example.weather.model.AlertData
import com.example.weather.model.getFormatDate


class AlertAdapter(var list:MutableList<AlertData>, var onDeletAlert: (AlertData)->Unit): RecyclerView.Adapter<AlertAdapter.ViewHolder>() {

    class ViewHolder(var rowView:AlertRowBinding): RecyclerView.ViewHolder(rowView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding=AlertRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int =list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.rowView.alerttype.text=list.get(position).type
        holder.rowView.alertdate.text=list.get(position).calendar.getFormatDate()

        holder.rowView.btncancle.setOnClickListener(View.OnClickListener {

            onDeletAlert(list.get(position))

        })
    }
}