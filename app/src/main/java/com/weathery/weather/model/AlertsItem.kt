package com.weathery.weather.model

import com.google.gson.annotations.SerializedName


data class AlertsItem(

        @field:SerializedName("start")
        val start: Long? = null,

        @field:SerializedName("description")
        val description: String? = null,

        @field:SerializedName("sender_name")
        val senderName: String? = null,

        @field:SerializedName("end")
        val end: Long? = null,

        @field:SerializedName("event")
        val event: String? = null
)
