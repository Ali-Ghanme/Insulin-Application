package com.example.diabestes_care_app.Ui.Sing_up_pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor.Sing_Up_1_D;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_1_P;

public class character_choice_screen extends Basic_Activity {
    Button btn_d;
    Button btn_p;
    Button log_in;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choice);

        btn_d = findViewById(R.id.doctor_btn);
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Sing_Up_1_D.class);
                startActivity(intent);
            }
        });

        btn_p = findViewById(R.id.patient_btn);
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Sing_Up_1_P.class);
                startActivity(intent);
            }
        });

        log_in = findViewById(R.id.log_page_btn);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Sing_In.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //==============================Casting=====================================================
        preferences = character_choice_screen.this.getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")) {
            Intent intent = new Intent(character_choice_screen.this, Home_Doctor.class);
            startActivity(intent);

        } else if (checkbox.equals("false")) {
            Toast.makeText(character_choice_screen.this, " Please Sign In", Toast.LENGTH_SHORT).show();
        }
    }
}