package com.example.weatherappcities.model;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


import com.example.weatherappcities.NotificActivity;
import com.example.weatherappcities.R;

import java.util.Calendar;

public class AMService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       String namecity=intent.getStringExtra("nMW");

        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), NotificActivity.class);
        // set up a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);


        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,3);
        calendar.set(Calendar.MINUTE,5);
        calendar.set(Calendar.SECOND,0);
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm Wanna Be!")
                .setContentText(namecity)
                .setSmallIcon(R.drawable.ic_menu_vector)
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();



        notify_manager.notify(0, notification_popup);
       // startForeground(1, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
