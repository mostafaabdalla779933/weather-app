package com.weathery.weather.util.locale


import java.util.*

/**
 *  Interface to be used by [LocaleManager] for storing a Locale and its complementary data.
 */
interface LocaleStore {
    fun getLocale(): Locale
    fun persistLocale(locale: Locale)

    fun setFollowSystemLocale(value: Boolean)
    fun isFollowingSystemLocale(): Boolean
}
