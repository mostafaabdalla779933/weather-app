package com.example.weather.model

import android.location.Location

public enum class Period{
    daily,hourly,minutely,alerts,current,
}

public class periodSetting{

    companion object{

        val FAVOURITE="minutely"
        val CURRENT="daily,hourly,minutely,alerts"

    }
}

public class Setting(var lang:String,var notification:Boolean,var temperUnit:String,var windSpeed:String,var location:Location?=null){

    companion object{
        val LANGUADE="language"
        val NOTIFICATION="notification"
        val TEMPERATURE="temperature"
        val WINDSPEED="windspeed"

        //*****************//

        val TIMEZONE="timeZone"
        val LNG="long"
        val LAT="lat"

        val  REPO="repo"
        val  ROOM="room"
        val  RETROFIT="retrofit"

        val REPEATING="repeating"

        val SWITCH="switch"
    }

}

public class Language {
    companion object{
        val ARABIC="ar"
        val ENGLISH="en"
    }
}

public class WindSpeed{

    companion object{
        val MPERSEC="mpersec"
        val MPERHOUR="mileperh"
    }


}


public class TemperUnit{

    companion object{
        val FAHRENHEIT="Fahrenheit"
        val CELSIUS="Celsius"
        val KELVIN="Kelvin"
    }

}

public class UnitApi{

    companion object{
        val STANDARD="standard"
        val METRIC="metric"
        val IMPRIAL="imperial"
    }
}




