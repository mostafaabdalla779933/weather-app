package com.weathery.weather.main.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.weathery.weather.fragments.alerts.view.AlertsFragment
import com.weathery.weather.fragments.favorite.view.FavoriteFragment
import com.weathery.weather.fragments.home.view.HomeFragment
import com.weathery.weather.fragments.setting.view.SettingFragment

class MainPagerAdapter(var fragment: Fragment):  FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> AlertsFragment()
        2 -> FavoriteFragment()
        3 -> SettingFragment()
        else -> HomeFragment()
    }

    override fun getItemCount(): Int = 4
}

