package com.doCompany.alazan

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_popp.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mAdView : AdView
    var cal= Calendar.getInstance()
    var y:Int = cal.get(Calendar.YEAR)
    var m:Int = cal.get(Calendar.MONTH+1)
    var d:Int = cal.get(Calendar.DAY_OF_MONTH)
    var city:String="Idleb"
    // val channel_ID="personal"
    val not_id=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // MobileAds.initialize(this,"ca-app-pub-8351493934827814~6188123033")
        //mAdView = findViewById(R.id.adView)
        //val adRequest = AdRequest.Builder().build()
        //mAdView.loadAd(adRequest)
        //setSupportActionBar(toolbar)
        getUsers(city)
        im_cal.setOnClickListener {
            calender() //calender dialoge
        }
        btn.setOnClickListener {
            getUsers(city)
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
                    "معرة النعمان"->{
                        city="Maarrat_al-Nu'man"
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
                    else->false
                }
            }
            popup.inflate(R.menu.items)
            popup.show()
        }
        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    /*  private fun notifa() {  notification
              var builder = NotificationCompat.Builder(this, channel_ID)
                  .setSmallIcon(R.drawable.ic_stat_name)
                  .setContentTitle("أوقات الأذان")
                  .setContentText("حان موعد الأذان حسب التوقيت المحلي لإدلب وماحولها")
                  .setSound(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.azan))
                  .setOnlyAlertOnce(true)
                  .setPriority(NotificationCompat.PRIORITY_DEFAULT)
              var notificationManagerCompat = NotificationManagerCompat.from(this)
              notificationManagerCompat.notify(not_id, builder.build())

      }*/

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
        val queue = Volley.newRequestQueue(this)
        val url: String =
            "http://api.aladhan.com/v1/calendarByCity?city=" + city + "&country=Syria&method=3&month=" + m + "&year=" + y

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String>
            { response ->
                // Toast.makeText(this,"good",Toast.LENGTH_LONG).show()

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

                pDialog.hide()
            },
            Response.ErrorListener {
            })
        queue.add(stringRequest)

    }
    lateinit var strResp:String
    lateinit var jsonOb: JSONObject
    lateinit var jsonArray: JSONArray
    lateinit var jsonInner: JSONObject
    var time: JSONObject = JSONObject()

    fun calender()
    {

        var date =object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                cal.set(Calendar.YEAR,year)
                cal.set(Calendar.MONTH,month)
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                y=year
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
}