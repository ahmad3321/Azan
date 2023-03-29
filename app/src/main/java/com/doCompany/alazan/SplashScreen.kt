package com.doCompany.alazan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_splash_screen)
        //startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        //finish()
        Handler().postDelayed(Runnable { // This method will be executed once the timer is over
            val i = Intent(this@SplashScreen, MainActivity2::class.java)
            startActivity(i)
            finish()
        }, 4000)

    }
}