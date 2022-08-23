package com.example.diabestes_care_app.Schedule_Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ReminderBoradcast extends BroadcastReceiver {
    public static String NOTIFICATION = "notification";
    public static String NOTIFICATIONID = "notification-id";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int imper = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(Notification_Alarm.NOTIFICATION_CHANEL_ID,"NOTIFICATION_CHANEL_NAME",imper);
            assert notificationManager !=null ;
            notificationManager.createNotificationChannel(notificationChannel);
            int id = intent.getIntExtra(NOTIFICATIONID,0);
            assert notificationManager !=null;
            notificationManager.notify(id,notification);
        }
    }
}
