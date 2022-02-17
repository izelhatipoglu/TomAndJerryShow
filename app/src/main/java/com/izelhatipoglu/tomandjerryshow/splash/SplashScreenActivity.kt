package com.izelhatipoglu.tomandjerryshow.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.izelhatipoglu.tomandjerryshow.MainActivity
import com.izelhatipoglu.tomandjerryshow.R
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val background = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(2000)
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}