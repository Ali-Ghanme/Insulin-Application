package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;

public class Sing_Up_4_D extends Basic_Activity {
    Button btn_next_S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_d);
        btn_next_S = findViewById(R.id.btn_Upload);
        //====================================Define variables===============================
        btn_next_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sing_Up_4_D.this, Home_Doctor.class);
                startActivity(intent);
            }
        });
    }
}