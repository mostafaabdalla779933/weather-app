package com.example.weather.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.example.weather.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun downloadIcon(icon:String?)="https://openweathermap.org/img/w/$icon.png"


fun Calendar.getFormatDate()= SimpleDateFormat("MMM d, yyyy hh:mm a").format(this.time)

fun Long.getDate()= SimpleDateFormat("MMM d, yyyy hh:mm a").format(this*1000)

fun Int.toCelsius()=this-273

fun Int.toFahrenheit()=(9*(this-273)/3 +32)

const  val KEY = "4b296deb770fc941bfd35a28581dc8b7"




fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}



fun setImgLottie(icon: String):Int{
    return when(icon){
        "01d" -> R.raw.dd01
        "02d" -> R.raw.dd02
        "03d" -> R.raw.dd03
        "04d" -> R.raw.dd04
        "09d" -> R.raw.dd09
        "10d" -> R.raw.dd10
        "11d" -> R.raw.dd11
        "13d" -> R.raw.dd13
        "50d" -> R.raw.dd50
        "01n" -> R.raw.n01
        "02n" -> R.raw.n02
        "03n" -> R.raw.n03
        "04n" -> R.raw.n04
        "09n" -> R.raw.n09
        "10n" -> R.raw.n10
        "11n" -> R.raw.n11
        "13n" -> R.raw.n13
        "50n" -> R.raw.n50
        else -> R.raw.d01
    }
}

