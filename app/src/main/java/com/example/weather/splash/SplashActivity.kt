package com.example.weather.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weather.R
import com.example.weather.databinding.SplashBinding
import com.example.weather.main.view.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class SplashActivity : AppCompatActivity() {

    lateinit var binding: SplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= SplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setAnimation(R.raw.splash)
        window.statusBarColor = resources.getColor(R.color.blue)
        window.navigationBarColor=resources.getColor(R.color.blue)

        lifecycleScope.launch{
            delay(3000L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }


}