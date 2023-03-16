package com.doCompany.alazan.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.doCompany.alazan.MainActivity;
import com.doCompany.alazan.R;


public class MyMediaService extends Service {

    MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start media playback
        try {

            Log.d("SERVICE", "service started true");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                NotificationChannel channel = new NotificationChannel(
                        "channelId",
                        "My Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );

                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                Intent intentt = new Intent(this, AlarmBroadcastReceiver.class);
                intentt.setAction("notification_cancelled");
                PendingIntent pend=PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);

                Notification notification = new Notification.Builder(this, "channelId")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("الأذان")
                        .setContentText("حان الان وقت الأذان")
                        .setSmallIcon(R.drawable.logo_awqafmini)
                        .build();
                startForeground(1, notification);
                stopForeground(false);
            }

            SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            if (prefs.getBoolean("enable_voice", true)) {
                mp = MediaPlayer.create(getApplicationContext(), R.raw.azan_sh);
                mp.start();
            }

        } catch (Exception e) {
            Log.e("error ",e.toString());
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        Log.d("Service", "Media stopped and released");

    }
}
