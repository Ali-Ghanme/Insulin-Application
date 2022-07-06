package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diabestes_care_app.Alarms.Alarm;
import com.example.diabestes_care_app.Alarms.alarmChooserActivity;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Doses_Home extends Basic_Activity {

    private Button button1 , button2;
//    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doses_home);

        button1 = findViewById(R.id.btn1_doses);
        button2 = findViewById(R.id.btn2_doses);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doses_Home.this, Alarm.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAlarmChooserActivity = new Intent();
                gotoAlarmChooserActivity.setClass(Doses_Home.this, alarmChooserActivity.class);
                startActivity(gotoAlarmChooserActivity);

            }
        });
    }
}