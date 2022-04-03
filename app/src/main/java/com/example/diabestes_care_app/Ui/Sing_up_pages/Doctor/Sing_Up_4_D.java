package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Sing_Up_4_D extends Basic_Activity {
    Button btn_next_S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_d);
        btn_next_S = findViewById(R.id.btn_next_3_dr);
        //====================================Define variables===============================
        btn_next_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home_D.class);
                startActivity(intent);
            }
        });
    }
}