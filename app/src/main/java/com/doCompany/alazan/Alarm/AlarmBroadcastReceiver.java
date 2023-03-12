package com.doCompany.alazan.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.doCompany.alazan.R;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("enable_voice", false)) {
            mp = MediaPlayer.create(context, R.raw.azan);
            mp.start();
        }

        Toast.makeText(context, "حان موعد الأذان", Toast.LENGTH_LONG).show();
    }
}