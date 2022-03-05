package com.example.diabestes_care_app.Ui.Sing_up_pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;

public class character_choice_screen extends Basic_Activity {
    Button btn_d;
    Button btn_p;
    Button log_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choice_screen);

        btn_d = findViewById(R.id.doctor_btn);
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Sing_Up_1.class);
                startActivity(intent);
            }
        });

        btn_p = findViewById(R.id.patient_btn);
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Sing_Up_1.class);
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
}