package com.example.diabestes_care_app.Schedule_Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Notification_Alarm extends AppCompatActivity {
    public static String NOTIFICATION_CHANEL_ID = "1001";
    public static String default_notification_id = "default";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_alarm);
        Button btn_notification = findViewById(R.id.btn_notification);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                scheduleNotification(getNotificatin("يرجى اخد الجرعات"), 1);
                            }
                        });
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        t.start();

//        public class SchedulerMain {
//            public static void main(String args[]) throws InterruptedException {
//                Timer time = new Timer();               // Instantiate Timer Object
//                ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
//                time.schedule(st, 0, 1000);             // Create task repeating every 1 sec
//                //for demo only.
//                for (int i = 0; i <= 5; i++) {
//                    System.out.println("Execution in Main Thread...." + i);
//                    Thread.sleep(2000);
//                    if (i == 5) {
//                        System.out.println("Application Terminates");
//                        System.exit(0);
//                    }
//                }
//            }
//        }
//import java.util.TimerTask;
//import java.util.Date;
//
//// Create a class extending TimerTask
//        public class ScheduledTask extends TimerTask {
//            Date now;
//
//            public void run() {
//                // Write code here that you want to execute periodically.
//                now = new Date();                      // initialize date
//                System.out.println("Time is :" + now); // Display current time
//            }
//        }

    }

    private void scheduleNotification(Notification notification, int delay) {
        Intent notifcationIntent = new Intent(this, ReminderBoradcast.class);
        notifcationIntent.putExtra(ReminderBoradcast.NOTIFICATIONID, 1);
        notifcationIntent.putExtra(ReminderBoradcast.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notifcationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futreMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)
                getSystemService(Content.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP
                , futreMillis, pendingIntent);
    }

    private Notification getNotificatin(String contect) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_id);
        builder.setContentTitle("hi title");
        builder.setContentText(contect);
        builder.setSmallIcon(R.drawable.ic_icon_material_attach_file);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANEL_ID);
        return builder.build();
    }

}