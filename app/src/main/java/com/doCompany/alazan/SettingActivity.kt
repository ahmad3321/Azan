package com.doCompany.alazan

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val swEnableVoice = findViewById<Switch>(R.id.Voice)
        if(!prefs.getBoolean("enable_voice",false)){
            swEnableVoice.isChecked=false
        }
        swEnableVoice.setOnCheckedChangeListener { buttonView, isChecked ->
            var editor = prefs.edit()
            editor = prefs.edit()
            if (isChecked) {
                editor.putBoolean("enable_voice", true)
            } else {
                editor.putBoolean("enable_voice", false)
            }
            editor.apply()
        }
    }
}