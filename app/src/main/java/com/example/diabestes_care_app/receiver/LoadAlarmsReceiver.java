package com.example.diabestes_care_app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.diabestes_care_app.data.model.Alarm_Model;
import com.example.diabestes_care_app.services.LoadAlarmsService;

import java.util.ArrayList;

public final class LoadAlarmsReceiver extends BroadcastReceiver {

    private OnAlarmsLoadedListener mListener;

    @SuppressWarnings("unused")
    public LoadAlarmsReceiver(){}

    public LoadAlarmsReceiver(OnAlarmsLoadedListener listener){
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final ArrayList<Alarm_Model> alarms =
                intent.getParcelableArrayListExtra(LoadAlarmsService.ALARMS_EXTRA);
        mListener.onAlarmsLoaded(alarms);
    }

    public void setOnAlarmsLoadedListener(OnAlarmsLoadedListener listener) {
        mListener = listener;
    }

    public interface OnAlarmsLoadedListener {
        void onAlarmsLoaded(ArrayList<Alarm_Model> alarms);
    }

}
