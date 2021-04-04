package com.example.weather.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.databinding.ActivityMapBinding
import com.example.weather.fragments.favorite.view.FavoriteFragment
import com.example.weather.fragments.setting.view.SettingFragment

class MapActivity : AppCompatActivity() {


    lateinit var binding: ActivityMapBinding
    val TAG="main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMapBinding.inflate(layoutInflater)

        var bundle=Bundle()
        Log.i(TAG, "onCreate: "+intent.getStringExtra("from"))

        when(intent.getStringExtra("from")){

            SettingFragment.Tag->bundle.putString("from",SettingFragment.Tag)

            FavoriteFragment.Tag->bundle.putString("from",FavoriteFragment.Tag)

        }

        setContentView(binding.root)
        setContentView(binding.root)

        val fragment:Fragment=MapsFragment()
        fragment.arguments= bundle

        supportFragmentManager.beginTransaction().add(R.id.mapcontainer, fragment).commit()

    }


}