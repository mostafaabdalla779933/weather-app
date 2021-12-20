package com.example.weather.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment:Fragment() {


    lateinit var binding:FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initViewPager()
        return binding.root
    }



    private fun initViewPager() {
        binding.viewpager.apply {
            adapter= MainPagerAdapter(requireActivity())
        }

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when(position){
                0->tab.text = getString(R.string.home)
                1->tab.text = getString(R.string.alerts)
                2->tab.text = getString(R.string.favorite)
                else->tab.text = getString(R.string.setting)
            }
        }.attach()

//        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
//            override fun onTabReselected(tab: TabLayout.Tab?) {}
//            override fun onTabUnselected(tab: TabLayout.Tab?) {}
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                binding.viewpager.setCurrentItem(tab!!.position)
//            }
//        })
    }




    companion object {
    }
}