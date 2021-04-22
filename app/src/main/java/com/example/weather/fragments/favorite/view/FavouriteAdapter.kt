package com.example.weather.fragments.favorite.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.databinding.FavRowBinding
import com.example.weather.model.Favourite

class FavouriteAdapter(var list:MutableList<Favourite>,var onItemDelete: (Favourite)->Unit,var onItemClick:(Favourite)->Unit): RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {


    val TAG="main"
    class ViewHolder(var rowView:FavRowBinding): RecyclerView.ViewHolder(rowView.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding=FavRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.rowView.textView2.text=list.get(position).name


        holder.rowView.imageButton.setOnClickListener(View.OnClickListener {

           onItemDelete(list.get(position))

        })

        holder.rowView.textView2.setOnClickListener(View.OnClickListener {
            onItemClick(list.get(position))
        })
    }

    override fun getItemCount(): Int =list.size

}
