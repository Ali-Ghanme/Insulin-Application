package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.Notification_Controller.LocalNotification.AlarmReceiver;
import com.example.diabestes_care_app.R;

import java.util.Calendar;

public class My_Exams_Fragment extends Fragment {

    // فحوصاتي
    Button Notefy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__exams_, container, false);

        Notefy = view.findViewById(R.id.Notefy);

        Notefy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);

                Intent notificationIntent = new Intent(getContext(), AlarmReceiver.class);
                PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, 5);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
            }
        });
        return view;
    }
}