package com.doCompany.alazan

import android.Manifest.permission.MODIFY_AUDIO_SETTINGS
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.doCompany.alazan.Alarm.AlarmManagement
import com.doCompany.alazan.Connection.SQLiteDAL
import com.doCompany.alazan.Models.Datum
import com.doCompany.alazan.Models.SalatRecord
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_popp.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.GeneralSecurityException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mAdView: AdView
    var cal = Calendar.getInstance()
    var y: Int = cal.get(Calendar.YEAR)
    var m: Int = cal.get(Calendar.MONTH + 1)
    var d: Int = cal.get(Calendar.DAY_OF_MONTH)
    var city: String = "Idleb"
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    var list: ArrayList<SalatRecord> = ArrayList()
    var url = ""
    var sqldal: SQLiteDAL = SQLiteDAL(null)
    var ChooseDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val perms = arrayOf(
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.MODIFY_AUDIO_SETTINGS"
        )
        val permsRequestCode = 200
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode)
        }
        //for animation background
        val frameAnimation: AnimationDrawable = linear.background as AnimationDrawable
        frameAnimation.setEnterFadeDuration(2000);
        frameAnimation.setExitFadeDuration(2000);
        frameAnimation.start()
        // MobileAds.initialize(this,"ca-app-pub-8351493934827814~6188123033")
        //mAdView = findViewById(R.id.adView)
        //val adRequest = AdRequest.Builder().build()
        //mAdView.loadAd(adRequest)
        //setSupportActionBar(toolbar)
        //getUsers(city)
        val sharedPreference = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
        val sh_city = sharedPreference.getString("city_Arabic", "")
        if (sh_city == null || sh_city == "") {
            city = "Idleb"
            txt_city.setText("إدلب")
        } else {
            city = sh_city
            txt_city.setText(city)
        }
        url = "v1/calendarByCity/" + Constants.year + "/" + Constants.month
        Constants.url = url
        sqldal = SQLiteDAL(this)
        ChooseDate = sdf.format(Date())
        var salatRecord = sqldal!!.getSalatRecord(ChooseDate)
        if (salatRecord == null) {
            sqldal.ClearTable(SQLiteDAL.TABLE_Salah_Time)
            if(!isDeviceOnline(this)){
                Toast.makeText(this,"لا يوجد اتصال بالانترنت",Toast.LENGTH_SHORT).show()
                return
            }
            getListTimesFromApi(this, city, "Syria", "3")
        } else {
            FillDataInView(salatRecord)
        }
        im_cal.setOnClickListener {
            calender(this) //calender dialoge
        }
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.azk1 ->
                startActivity(Intent(this, Azkar_1::class.java))
            R.id.nav_share -> {
                share()
            }
            R.id.item_city -> {
                showPopup(findViewById(R.id.txt_city))
            }
            R.id.azkar_counter -> {
                startActivity(Intent(this, CounterActivity::class.java))

            }
            R.id.nav_about -> {
                val intent = Intent(this, popp::class.java)
                startActivity(intent)
            }
            R.id.nav_developer -> {
                val intent = Intent(this, developer::class.java)
                startActivity(intent)
            }
            R.id.nav_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun getUsers(city: String) {
        // Instantiate the RequestQueue.
        val pDialog: ProgressDialog
        pDialog = ProgressDialog(this)
        pDialog.setMessage("Loading...")
        pDialog.show()
        //val queue = Volley.newRequestQueue(applicationContext)
        // geet(url,applicationContext)
        // Request a string response from the provided URL.

        /*val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    strResp = response.toString()
                    jsonOb = JSONObject(strResp)
                    jsonArray = jsonOb.getJSONArray("data")
                    jsonInner = jsonArray.getJSONObject(d + 1)
                    time = jsonInner.getJSONObject("timings")
                    var fajar = time.getString("Fajr")


                    t_faj1.text = fajar.substring(0, 5)
                    t_duha1.text = time.getString("Sunrise").substring(0, 5)
                    t_dhuhor1.text = time.getString("Dhuhr").substring(0, 5)
                    t_asr1.text = time.getString("Asr").substring(0, 5)
                    t_moghrib1.text = time.getString("Maghrib").substring(0, 5)
                    t_eshaa1.text = time.getString("Isha").substring(0, 5)
                }catch (ex:Exception){
                    pDialog.hide()
                }

        }, { error ->
            Log.e("TAG", "RESPONSE IS $error")
        })
        queue.add(request)*/
    }

    private fun getListTimesFromApi(
        cotext: Context,
        city: String,
        country: String,
        method: String
    ) {
        //var salatrecord: SalatRecord =SalatRecord(null,null,null,null,null,null,null,null)
        list = ArrayList()
        val pDialog: ProgressDialog
        pDialog = ProgressDialog(cotext)
        pDialog.setMessage("جاري تحميل مواقيت الأذان ..")
        pDialog.setCancelable(false);
        try {
            // launching a new coroutine
            GlobalScope.launch {

                val h = Handler(Looper.getMainLooper())
                h.post(Runnable {
                    pDialog.show()
                })
                for (i in 1..12) {
                    url = "v1/calendarByCity/" + Constants.year + "/" + i
                    val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
                    val result = quotesApi.getQuotes(url, city, country, method)
                    if (result != null) {
                        //Log.d("ayush: ", result.body().toString())
                        var modal: List<Datum> = result.body()!!.data
                        for (j in 0 until modal.size) {
                            var date = modal.get(j).date.gregorian.date
                            var t_Imsak = modal.get(j).timings.Imsak.substring(0, 5)
                            var t_faj1 = modal.get(j).timings.Fajr.substring(0, 5)
                            var t_duha = modal.get(j).timings.Sunrise.substring(0, 5)
                            var t_dhuhor = modal.get(j).timings.Dhuhr.substring(0, 5)
                            var t_asr = modal.get(j).timings.Asr.substring(0, 5)
                            var t_moghrib = modal.get(j).timings.Maghrib.substring(0, 5)
                            var t_eshaa = modal.get(j).timings.Isha.substring(0, 5)
                            list.add(
                                SalatRecord(
                                    date,
                                    t_Imsak,
                                    t_faj1,
                                    t_duha,
                                    t_dhuhor,
                                    t_asr,
                                    t_moghrib,
                                    t_eshaa
                                )
                            )
                        }
                    }
                }
                h.post(Runnable {
                    pDialog.hide()
                })
                sqldal.addListOfDays(list)
                val salatRecord = sqldal!!.getSalatRecord(ChooseDate)
                FillDataInView(salatRecord)
                setNextAlarm(applicationContext, salatRecord.date)
            }
        } catch (exs: Exception) {
            Log.d("ayush: ", exs as String)
            pDialog.hide()
            //return emptyList()
        }
        //return list
    }

    fun calender(context: Context) {
        var date = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                val calendar = Calendar.getInstance()
                if (year != calendar.get(Calendar.YEAR)) {
                    Toast.makeText(
                        applicationContext,
                        "السنة مختلفة عن السنة الحالية",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                cal.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                //y=calendar.get(Calendar.YEAR)
                //m=month+1
                //d=dayOfMonth
                ChooseDate = sdf.format(cal.time)
                var salatRecord = sqldal!!.getSalatRecord(ChooseDate)
                if (salatRecord == null) {
                    sqldal.ClearTable(SQLiteDAL.TABLE_Salah_Time)
                    getListTimesFromApi(context, city, "Syria", "3")
                } else {
                    FillDataInView(salatRecord)
                }
            }
        }
        DatePickerDialog(
            this,
            date,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun share() {
        var intent = Intent(Intent.ACTION_SEND).setType("text/plain")
        var body = "https://play.google.com/store/apps/details?id=com.agha.azan"
        var sub = "تطبيق الأذان في المحرر"
        intent.putExtra(Intent.EXTRA_SUBJECT, sub)
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(intent, R.string.share_.toString()))

    }

    fun FillDataInView(salatRecord: SalatRecord) {
        t_imsak1.text = salatRecord.imsak
        t_faj1.text = salatRecord.fajr
        t_duha1.text = salatRecord.duha
        t_dhuhor1.text = salatRecord.dhuhor
        t_asr1.text = salatRecord.asr
        t_moghrib1.text = salatRecord.moghrib
        t_eshaa1.text = salatRecord.eshaa
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.items)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            /* var bool:Boolean  =false;
             val alertbox = AlertDialog.Builder(this)
             alertbox.setTitle("تأكيد")
             alertbox.setMessage("هل تريد حفظ المعلومات؟")
             alertbox.setPositiveButton(
                 "نعم"
             ) { arg0, arg1 -> bool =true}
             alertbox.setNegativeButton(
                 "لا"
             ) { arg0, arg1 -> return@setNegativeButton }
             alertbox.show()*/

            when (item!!.title) {
                "إدلب" -> {
                    city = "Idleb"
                }
                "حلب" -> {
                    city = "Aleppo"
                }
                "جسر الشغور" -> {
                    city = "Jisr_al-Shughour"
                }
                "سلقين" -> {
                    city = "Salqin"
                }
                "سرمدا" -> {
                    city = "Sarmada"
                }
                "عفرين" -> {
                    city = "Afrin"
                }
                "الباب" -> {
                    city = "Al_bab"
                }
                "أطمة" -> {
                    city = "Atme"
                }
                "اعزاز" -> {
                    city = "Azaz"
                }
                "منبج" -> {
                    city = "Manbij"
                }
            }
            val sharedPreference = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
            var sh_city = sharedPreference.getString("city", "")
            if (sh_city == city) {

            } else {
                txt_city.setText(item!!.title)
                sqldal.ClearTable(SQLiteDAL.TABLE_Salah_Time)
                var salatRecord = sqldal!!.getSalatRecord(ChooseDate)
                getListTimesFromApi(this, city, "Syria", "3")
                try {
                    val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)

                    val myEdit = sharedPreferences.edit()
                    // Storing the key and its value as the data fetched from edittext
                    myEdit.putString("city", city)
                    myEdit.putString("city_Arabic", item!!.title.toString())
                    myEdit.apply()

                    AlarmManagement.cancleAlarm(this.applicationContext)
                } catch (e: GeneralSecurityException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            true
        })

        popup.show()
    }

    companion object MyCompanion {

        @SuppressLint("SimpleDateFormat")
        fun setNextAlarm(context: Context, date: String) {
            try {
                val qlitDal = SQLiteDAL(context)
                val sdf1 = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                val salatRecord = qlitDal.getSalatRecord(date)

                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val formatter =
                    SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US)
                var dateTimeString = ""

                if (hour < Integer.parseInt(salatRecord.imsak.substring(0, 2))
                    || (hour == Integer.parseInt(salatRecord.imsak.substring(0, 2))
                            && minute < Integer.parseInt(salatRecord.imsak.substring(3)) - 5
                            )
                ) {
                    dateTimeString = sdf1.format(Date()) + " " + salatRecord.imsak.substring(
                        0,
                        2
                    ) + ":" + salatRecord.imsak.substring(3)
                } else if (hour < Integer.parseInt(salatRecord.fajr.substring(0, 2))
                    || (hour == Integer.parseInt(salatRecord.fajr.substring(0, 2))
                            && minute < Integer.parseInt(salatRecord.fajr.substring(3)) - 5
                            )
                ) {
                    dateTimeString = sdf1.format(Date()) + " " + salatRecord.fajr.substring(
                        0,
                        2
                    ) + ":" + salatRecord.fajr.substring(3)

                } else if (hour < Integer.parseInt(salatRecord.dhuhor.substring(0, 2))
                    || (hour == Integer.parseInt(salatRecord.dhuhor.substring(0, 2))
                            && minute < Integer.parseInt(salatRecord.dhuhor.substring(3)) - 5
                            )
                ) {
                    dateTimeString = sdf1.format(Date()) + " " + salatRecord.dhuhor.substring(
                        0,
                        2
                    ) + ":" + salatRecord.dhuhor.substring(3)
                } else if (hour < Integer.parseInt(salatRecord.asr.substring(0, 2))
                    || (hour == Integer.parseInt(salatRecord.asr.substring(0, 2))
                            && minute < Integer.parseInt(salatRecord.asr.substring(3)) - 5
                            )
                ) {
                    dateTimeString = sdf1.format(Date()) + " " + salatRecord.asr.substring(
                        0,
                        2
                    ) + ":" + salatRecord.asr.substring(3)
                } else if (hour < Integer.parseInt(salatRecord.moghrib.substring(0, 2))
                    || (hour == Integer.parseInt(salatRecord.moghrib.substring(0, 2))
                            && minute < Integer.parseInt(salatRecord.moghrib.substring(3)) - 5
                            )
                ) {
                    dateTimeString = sdf1.format(Date()) + " " + salatRecord.moghrib.substring(
                        0,
                        2
                    ) + ":" + salatRecord.moghrib.substring(3)

                } else if (hour < Integer.parseInt(salatRecord.eshaa.substring(0, 2))
                    || (hour == Integer.parseInt(salatRecord.eshaa.substring(0, 2))
                            && minute < Integer.parseInt(salatRecord.eshaa.substring(3)) - 5
                            )
                ) {
                    dateTimeString = sdf1.format(Date()) + " " + salatRecord.eshaa.substring(
                        0,
                        2
                    ) + ":" + salatRecord.eshaa.substring(3)
                }

                //if after eshaa, then set imsak for the next day
                else {
                    val today = formatter.parse(date)
                    val calendar = Calendar.getInstance()

                    calendar.setTime(today)
                    calendar.add(Calendar.DAY_OF_YEAR, 1)

                    val tomorrwo = calendar.time
                    val salatRecord_tomorrwo = qlitDal.getSalatRecord(sdf1.format(tomorrwo))
                    dateTimeString =
                        sdf1.format(Date()) + " " + salatRecord_tomorrwo.imsak.substring(
                            0,
                            2
                        ) + ":" + salatRecord_tomorrwo.imsak.substring(3)
                }
                AlarmManagement.setAlarmAt(context, formatter.parse(dateTimeString))
            } catch (ex: Exception) {
                // Toast.makeText(context,"حدث خطأ ما",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isDeviceOnline(context: Context): Boolean {
        val connManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connManager.getNetworkCapabilities(connManager.activeNetwork)
            if (networkCapabilities == null) {
                Log.d("tagLog", "Device Offline")
                return false
            } else {
                Log.d("tagLog", "Device Online")
                return true
            }
        } else {
            // below Marshmallow
            val activeNetwork = connManager.activeNetworkInfo
            if (activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable) {
                Log.d("tagLog", "Device Online")
                return true
            } else {
                Log.d("tagLog", "Device Offline")
                return false
            }
        }
    }

}