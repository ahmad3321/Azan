package com.doCompany.alazan

import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_setting.*


class SettingActivity : AppCompatActivity() {
    //val voicesAzan = arrayOf("azan_shoayb", "azan_alrahma", "azan_idleb_jubaily", "azan_salqin", "azan_sarmada")
    val voicesAzan = arrayOf( "أنس خطاب", "محمد ربيع تسون", "وليد جبيلي", "محمد مصطفى بكور", "أحمد عبيد")
    lateinit var selectedvoicesAzan: String
    var selectedvoicesAzanIndex: Int = 0
    var VoiceRaw = "azan_shoayb"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        //azan voices exist in app

        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        Voice.isChecked = prefs.getBoolean("enable_voice",true)
        Voice.setOnCheckedChangeListener { buttonView, isChecked ->
            var editor = prefs.edit()
            editor = prefs.edit()
            if (isChecked) {
                editor.putBoolean("enable_voice", true)
            } else {
                editor.putBoolean("enable_voice", false)
            }
            editor.apply()
        }
        azan_voice.setOnClickListener {
            showRadioConfirmationDialog()
        }
    }
    private fun showRadioConfirmationDialog() {
        selectedvoicesAzan = voicesAzan[selectedvoicesAzanIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle("أصوات الأذان")
            .setSingleChoiceItems(voicesAzan, selectedvoicesAzanIndex) { dialog_, which ->
                selectedvoicesAzanIndex = which
                selectedvoicesAzan = voicesAzan[which]
            }
            .setPositiveButton("Ok") { dialog, which ->
                //get selected voice and set
                when(selectedvoicesAzanIndex){
                    0 -> VoiceRaw = "azan_shoayb"
                    1 -> VoiceRaw = "azan_alrahma"
                    2 -> VoiceRaw = "azan_idleb_jubaily"
                    3 -> VoiceRaw = "azan_salqin"
                    4 -> VoiceRaw = "azan_sarmada"
                }
                val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                var editor = prefs.edit()
                editor.putString("voice_raw", VoiceRaw)
                editor.apply()
                //Toast.makeText(this, "$selectedvoicesAzanIndex Selected", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }
}