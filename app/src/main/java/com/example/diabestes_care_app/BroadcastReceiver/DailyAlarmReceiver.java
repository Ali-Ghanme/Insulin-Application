package com.example.diabestes_care_app.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DailyAlarmReceiver extends BroadcastReceiver {
    public int counter;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DailyAlarmReceiver", "Received");
        Toast.makeText(context, "This is a Finished", Toast.LENGTH_SHORT).show();

    }
}