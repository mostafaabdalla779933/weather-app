package com.example.weather.main.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.weather.fragments.alerts.view.AlertsFragment
import com.example.weather.fragments.favorite.view.FavoriteFragment
import com.example.weather.fragments.home.view.HomeFragment
import com.example.weather.fragments.setting.view.SettingFragment

class MainPagerAdapter(var fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> AlertsFragment()
        2 -> FavoriteFragment()
        3 -> SettingFragment()
        else -> HomeFragment()
    }

    override fun getCount(): Int = 4
}