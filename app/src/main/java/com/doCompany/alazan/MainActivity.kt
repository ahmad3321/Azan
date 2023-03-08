package com.doCompany.alazan

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.preference.PreferenceManager
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
    lateinit var mAdView : AdView
    var cal= Calendar.getInstance()
    var y:Int = cal.get(Calendar.YEAR)
    var m:Int = cal.get(Calendar.MONTH+1)
    var d:Int = cal.get(Calendar.DAY_OF_MONTH)
    var city:String="Idleb"
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var list:ArrayList<SalatRecord> =ArrayList()
    var url =""
    var sqldal: SQLiteDAL =SQLiteDAL(null)


    // val channel_ID="personal"
    val not_id=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val perms = arrayOf(
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE"
        )
        val permsRequestCode = 200
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode)
        }
        // MobileAds.initialize(this,"ca-app-pub-8351493934827814~6188123033")
        //mAdView = findViewById(R.id.adView)
        //val adRequest = AdRequest.Builder().build()
        //mAdView.loadAd(adRequest)
        //setSupportActionBar(toolbar)
        //getUsers(city)
        url = "v1/calendarByCity/"+Constants.year+"/"+Constants.month
        Constants.url=url
        sqldal =SQLiteDAL(this)
        val currentDate = sdf.format(Date())
        var salatRecord =  sqldal!!.getSalatRecord(currentDate)
        if(salatRecord==null){
            sqldal.ClearTable(SQLiteDAL.TABLE_Salah_Time)
           // FillAllYearData(this)
            getListTimesFromApi(this,city,"Syria","2")
        }

        im_cal.setOnClickListener {
            //calender() //calender dialoge
            var salatRecord =  sqldal!!.getSalatRecord(currentDate)
            if(salatRecord==null){
                Log.d("","null")
            }
        }

        btn_city.setOnClickListener {
            val popup= PopupMenu(this,it)
            popup.setOnMenuItemClickListener {item ->
                when(item.title)
                {
                    "إدلب"->{
                        city="Idleb"
                        true
                    }
                    "حلب"->{
                        city="Aleppo"
                        true
                    }
                    "جسر الشغور"->{
                        city="Jisr_al-Shughour"
                        true
                    }
                    "سلقين"->{
                        city="Salqin"
                        true
                    }
                    "سرمدا"->{
                        city="Sarmada"
                        true
                    }
                    "عفرين"->{
                        city="Afrin"
                        true
                    }
                    "الباب"->{
                        city="Al_bab"
                        true
                    }
                    "أطمة"->{
                        city="Atme"
                        true
                    }
                    "اعزاز"->{
                        city="Azaz"
                        true
                    }
                    "منبج"->{
                        city="Manbij"
                        true
                    }
                    else->false
                }

            }
            try {
                val sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val myEdit = sharedPreferences1.edit()
                myEdit.putString("city", city)
                myEdit.apply()
            } catch (e: GeneralSecurityException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            popup.inflate(R.menu.items)
            popup.show()
        }
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_about ->
                startActivity(Intent(this, popp::class.java))
            R.id.action_developer ->
                startActivity(Intent(this, developer::class.java))
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.azk1 ->
                startActivity(Intent(this,Azkar_1::class.java))
            R.id.nav_share -> {
                share()
            }
            R.id.nav_about -> {
                val intent = Intent(this, popp::class.java)
                startActivity(intent)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    fun getUsers(city:String) {
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
    private fun getListTimesFromApi(cotext:Context,city: String,country:String,method:String){
        //var salatrecord: SalatRecord =SalatRecord(null,null,null,null,null,null,null,null)
        list =ArrayList()
        val pDialog: ProgressDialog
        pDialog = ProgressDialog(cotext)
        pDialog.setMessage("Loading...")
        pDialog.setCancelable(false);
        try{
            // launching a new coroutine
            GlobalScope.launch {
                val h = Handler(Looper.getMainLooper())
                h.post(Runnable {
                    pDialog.show()
                })
                for( i in 1..12) {
                    url = "v1/calendarByCity/" + Constants.year + "/" + i
                    val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
                    val result = quotesApi.getQuotes(url, city, country, method)
                    if (result != null) {
                        //Log.d("ayush: ", result.body().toString())
                        var modal: List<Datum> = result.body()!!.data
                        for (j in 1 until modal.size) {
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
                            /*Log.d(
                                "result: ",
                                " date: " + date + " t_faj1: " + t_faj1 + " t_duha: " + t_duha + " t_dhuhor: " + t_dhuhor + " t_asr: " + t_asr +
                                        " t_moghrib: " + t_moghrib + " t_eshaa: " + t_eshaa
                            )*/
                        }
                    }
                }
                h.post(Runnable {
                    pDialog.hide()
                })
                sqldal.addListOfDays(list)
            }
        }catch (exs:Exception){
            Log.d("ayush: ", exs as String)
            pDialog.hide()
            //return emptyList()
        }
        //return list
    }
    fun calender()
    {
        var date =object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                val calendar = Calendar.getInstance()
                if(year!=calendar.get(Calendar.YEAR)){
                    Toast.makeText(applicationContext, "السنة مختلفة عن السنة الحالية", Toast.LENGTH_SHORT).show()
                }
                cal.set(Calendar.YEAR,calendar.get(Calendar.YEAR))
                cal.set(Calendar.MONTH,month)
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                y=calendar.get(Calendar.YEAR)
                m=month+1
                d=dayOfMonth
            }
        }
        DatePickerDialog(this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    fun share()
    {
        var intent= Intent(Intent.ACTION_SEND).setType("text/plain")
        var body="https://play.google.com/store/apps/details?id=com.agha.azan"
        var sub="تطبيق الأذان في المحرر"
        intent.putExtra(Intent.EXTRA_SUBJECT,sub)
        intent.putExtra(Intent.EXTRA_TEXT,body)
        startActivity(Intent.createChooser(intent, R.string.share_.toString()))

    }
    fun FillAllYearData(context: Context)
    {
        val salatRecords = ArrayList<SalatRecord>()
        for(int in 1..2){
            //salatRecords.add(getTimesFromApi(context,city,"Syria","2")!!)

        }
        Log.d("","")
    }
}