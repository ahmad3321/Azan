package com.doCompany.alazan

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.doCompany.alazan.Connection.SQLiteDAL
import java.util.Calendar

class Constants {
    companion object {
        val year: Int = Calendar.getInstance().get(Calendar.YEAR)
        val month: Int = Calendar.getInstance().get(Calendar.MONTH) + 1
        var url: String = ""
    }

}