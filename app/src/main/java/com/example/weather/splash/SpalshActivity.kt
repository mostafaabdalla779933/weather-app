package com.example.weather.splash

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.R
import com.example.weather.data.local.sharedpref.Sharedprefer
import com.example.weather.databinding.SplashBinding
import com.example.weather.main.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class SpalshActivity : AppCompatActivity() {

    lateinit var binding: SplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= SplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

       setLocale(Sharedprefer.getlanguge())



        binding.imageView.setAnimation(R.raw.splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            startActivity(Intent(this@SpalshActivity, MainActivity::class.java))
            finish()
        }

    }

    fun setLocale(lang:String ) {
        val resources: Resources = this.getResources()
        val locale = Locale(lang)
        Locale.setDefault(locale);
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }
}