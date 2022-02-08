package com.example.weather.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weather.R
import com.example.weather.databinding.SplashBinding
import com.example.weather.main.view.MainActivity
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class SplashActivity : AppCompatActivity() {

    lateinit var binding: SplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= SplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setAnimation(R.raw.splash)
        window.statusBarColor = getColor(R.color.blue)
        window.navigationBarColor=resources.getColor(R.color.blue)

        lifecycleScope.launch{
            firebaseToken()
            delay(3000L)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }


    private fun firebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.i("main", "firebaseToken: ${task.result}")
            }else{
                Log.i("main", "firebaseToken: fail2")
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("weather")
            .addOnFailureListener { e->
                Log.i("main", "firebaseToken: fail1 ${e.message}")
            }.addOnCompleteListener { task ->
                var msg = "success"
                if (!task.isSuccessful) {
                    msg = "fail"
                }
                Log.i("main", "firebaseToken4: $msg")
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }


}