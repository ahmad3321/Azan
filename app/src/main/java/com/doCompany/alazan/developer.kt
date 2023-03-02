package com.doCompany.alazan

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import com.doCompany.alazan.R


class developer: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.developer)
       var s: DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(s)
        var width=s.widthPixels
        var hei=s.heightPixels
        window.setLayout(((width*0.8).toInt()),(hei*0.6).toInt())
    }

}