package com.doCompany.alazan

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        val swEnableVoice = findViewById<Switch>(R.id.Voice)

        swEnableVoice.setOnCheckedChangeListener { buttonView, isChecked ->

            val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = prefs.edit()
            if (isChecked) {
                editor.putBoolean("enable_voice", true)
            } else {
                editor.putBoolean("enable_voice", false)
            }
            editor.apply()
        }
    }
}