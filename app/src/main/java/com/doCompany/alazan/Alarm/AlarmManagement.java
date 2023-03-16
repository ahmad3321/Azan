package com.doCompany.alazan.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class AlarmManagement {

    public static void setAlarmAfter(Context context, long afterSeconds) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (afterSeconds * 1000), pendingIntent);
//        Toast.makeText(context, "Alarm set in " + afterSeconds + " seconds", Toast.LENGTH_LONG).show();
    }


    public static void setAlarmAt(Context context, @NonNull Date salatDate) {
        Date currentTime = Calendar.getInstance().getTime();
        long afterMilliSeconds = salatDate.getTime() - currentTime.getTime();
        Log.d("after : ", String.valueOf(afterMilliSeconds));
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + afterMilliSeconds, pendingIntent);
//        Toast.makeText(context.getApplicationContext(), "after " + afterMilliSeconds, Toast.LENGTH_LONG).show();
    }

    public static void cancleAlarm(Context context) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
