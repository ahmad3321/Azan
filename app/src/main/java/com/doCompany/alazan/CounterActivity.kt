package com.doCompany.alazan

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doCompany.alazan.Models.RandomColor
import kotlinx.android.synthetic.main.activity_counter.*

class CounterActivity : AppCompatActivity() {



    private val MY_PREFS_NAME = "pref"
    private var sharedPreferences: SharedPreferences? = null

    private var counts = 0
    private var totalCounts = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        getDataFromSharedPreferences()

        counterscreen.setOnClickListener {
            counts++
            totalCounts++

            //checkCounts(counts)
            countertextview.text = counts.toString()
            setTotalZekerCountstext(totalCounts)

            saveTotalcountsInSharedPreference()
        }
        resetbtn.setOnClickListener {
            counts = 0
            countertextview.text = counts.toString()
            //countertextview.setTextColor(Color.WHITE)
        }
    }
    fun getDataFromSharedPreferences() {
        sharedPreferences = applicationContext?.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        totalCounts = sharedPreferences!!.getInt("zekertotalcounts", totalCounts)
        setTotalZekerCountstext(totalCounts)
    }
    @SuppressLint("SetTextI18n")
    fun setTotalZekerCountstext(Totalzekercounts: Int) {
        totalCountstextview.text = getString(R.string.totalzeker) + "  " + Totalzekercounts
    }

    private fun checkCounts(counts: Int) {
        if (counts % 33 == 0) getRandomcolor()
    }
    private fun getRandomcolor() {
        val currentColor: Int
        if (counts % 33 == 0) {
            currentColor = RandomColor.getRandomColor()
            countertextview.setTextColor(currentColor)
        }
    }
    private fun saveTotalcountsInSharedPreference() {
        val editor = sharedPreferences!!.edit()
        editor.putInt("zekertotalcounts", totalCounts)
        editor.apply()
    }
}