package com.example.diabestes_care_app.Notification_Controller;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.diabestes_care_app.R;

public class Notification_Number {
     TextView notificationNumber;
     final int MAX_Number = 99;
     int notification_number_counter = 0;

    public Notification_Number(View view){
        notificationNumber = view.findViewById(R.id.notification_Number);
    }

    public void incrementNumber(){
        notification_number_counter++;
        if (notification_number_counter > MAX_Number){
            Log.d("Counter","Maximum Number Reached");
        }else {
            notificationNumber.setText(String.valueOf(notification_number_counter));
        }
    }
}
