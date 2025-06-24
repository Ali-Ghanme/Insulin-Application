package com.example.diabestes_care_app.receiver;

import static android.content.Intent.ACTION_BOOT_COMPLETED;
import static com.example.diabestes_care_app.receiver.AlarmReceiver.setReminderAlarms;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.diabestes_care_app.data.local.AlarmDatabaseHelper;
import com.example.diabestes_care_app.data.model.Alarm_Model;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Re-schedules all stored alarms. This is necessary as {@link AlarmManager} does not persist alarms
 * between reboots.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Executors.newSingleThreadExecutor().execute(() -> {
                final List<Alarm_Model> alarms = AlarmDatabaseHelper.getInstance(context).getAlarms();
                setReminderAlarms(context, alarms);
            });
        }
    }

}
