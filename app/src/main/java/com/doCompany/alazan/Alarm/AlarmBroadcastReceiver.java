package com.doCompany.alazan.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.doCompany.alazan.MainActivity;
import com.doCompany.alazan.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {

            Intent i = new Intent(context, MyMediaService.class);
            context.startService(i);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            MainActivity.MyCompanion.setNextAlarm(context.getApplicationContext(), sdf.format(new Date()));

            //Toast.makeText(context, "حان موعد الأذان", Toast.LENGTH_LONG).show();
    }
}