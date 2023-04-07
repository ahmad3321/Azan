package com.doCompany.alazan

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.doCompany.alazan.Qibla.Compass
import com.doCompany.alazan.Qibla.Compass.CompassListener
import com.doCompany.alazan.Qibla.SOTWFormatter


class QiblaActivity : AppCompatActivity() {
    private var compass: Compass? = null
    private var arrowView: ImageView? = null
    private var sotwLabel // SOTW is for "side of the world"
            : TextView? = null

    private var currentAzimuth = 0f
    private var sotwFormatter: SOTWFormatter ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qibla)


        sotwFormatter = SOTWFormatter(this);

        arrowView = findViewById(R.id.main_image_hands);
        sotwLabel = findViewById(R.id.sotw_label);
        setupCompass();
    }
    override fun onStart() {
        super.onStart()
        Log.d("TAG", "start compass")
        compass!!.start()
    }
    override fun onPause() {
        super.onPause()
        compass!!.stop()
    }

    override fun onResume() {
        super.onResume()
        compass!!.start()
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "stop compass")
        compass!!.stop()
    }
    private fun setupCompass() {
        compass = Compass(this)
        val cl: CompassListener = getCompassListener()!!
        compass!!.setListener(cl)
    }
    private fun adjustArrow(azimuth: Float) {
        Log.d(
            "TAG", "will set rotation from " + currentAzimuth + " to "
                    + azimuth
        )
        val an: Animation = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        arrowView!!.startAnimation(an)
    }

    private fun adjustSotwLabel(azimuth: Float) {
        sotwLabel!!.text = sotwFormatter!!.format(azimuth)
    }

    private fun getCompassListener(): CompassListener? {
        return CompassListener { azimuth -> // UI updates only in UI thread
            // https://stackoverflow.com/q/11140285/444966
            runOnUiThread {
                adjustArrow(azimuth)
                adjustSotwLabel(azimuth)
            }
        }
    }
}