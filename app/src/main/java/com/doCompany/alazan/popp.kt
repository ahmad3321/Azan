package com.doCompany.alazan

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.doCompany.alazan.R

class popp: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.popp)
        var m:DisplayMetrics= DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(m)
        var width=m.widthPixels
        var hei=m.heightPixels
        window.setLayout(((width*0.8).toInt()),(hei*0.6).toInt())
    }

}
