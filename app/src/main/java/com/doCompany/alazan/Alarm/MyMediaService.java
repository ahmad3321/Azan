package com.doCompany.alazan.Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

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

                Notification notification = new Notification.Builder(this, "channelId")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Service is running")
                        .setContentText("Your service is performing an important operation")
                        .build();
                startForeground(1, notification);
            }

            mp = MediaPlayer.create(getApplicationContext(), R.raw.azan);
            mp.start();

        } catch (Exception e) {
            // Handle exception
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
