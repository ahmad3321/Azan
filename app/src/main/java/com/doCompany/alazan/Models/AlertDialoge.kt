package com.doCompany.alazan.Models

import android.app.Activity
import android.app.AlertDialog


fun ExitAppDialog(activity: Activity,title:String,message:String) {
    val alertbox = AlertDialog.Builder(activity)
    alertbox.setTitle("title")
    alertbox.setMessage(message)
    alertbox.setPositiveButton(
        "نعم"
    ) { arg0, arg1 -> activity.finish() }
    alertbox.setNegativeButton(
        "لا"
    ) { arg0, arg1 -> }
    alertbox.show()
}