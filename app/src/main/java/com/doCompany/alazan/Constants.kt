package com.doCompany.alazan

import java.util.Calendar

class Constants {
    companion object {
        val year:Int =Calendar.getInstance().get(Calendar.YEAR)
        val month:Int = Calendar.getInstance().get(Calendar.MONTH)+1
        var url:String = ""

    }
}