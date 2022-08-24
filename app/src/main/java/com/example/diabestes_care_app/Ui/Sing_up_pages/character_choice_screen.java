package com.example.diabestes_care_app.Ui.Sing_up_pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor.Sing_Up_1_D;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_1_P;

public class character_choice_screen extends Basic_Activity {
    Button btn_d;
    Button btn_p;
    TextView log_in;
    SharedPreferences preferences_D, preferences_P;

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
        //============[عند عملية حفظ المستخدم بالتطبيق والرجوع يتم فتح التطبيق مباشرة ]==============

        //==============================Casting=====================================================
        preferences_P = character_choice_screen.this.getSharedPreferences("checkbox_P", MODE_PRIVATE);
        String checkbox_P = preferences_P.getString("remember_P", "");
        if (checkbox_P.equals("true")) {
            Intent intent_P = new Intent(character_choice_screen.this, Home_Patient.class);
            startActivity(intent_P);
            finish();
        }
        preferences_D = character_choice_screen.this.getSharedPreferences("checkbox_D", MODE_PRIVATE);
        String checkbox_D = preferences_D.getString("remember_D", "");
        if (checkbox_D.equals("true")) {
            Intent intent_D = new Intent(character_choice_screen.this, Home_Doctor.class);
            startActivity(intent_D);
            finish();
        }
    }
    // Hallow this is update

}