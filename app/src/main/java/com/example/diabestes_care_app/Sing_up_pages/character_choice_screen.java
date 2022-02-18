package com.example.diabestes_care_app.Sing_up_pages;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.example.diabestes_care_app.Home_Activitey;
import com.example.diabestes_care_app.MainActivity;
import com.example.diabestes_care_app.R;

public class character_choice_screen extends AppCompatActivity {
    Button btn_d;
    Button btn_p;
    Button log_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choice_screen);

        btn_d = findViewById(R.id.doctor_btn);
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_p = findViewById(R.id.patient_btn);
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Home_Activitey.class);
                startActivity(intent);
            }
        });

        log_in = findViewById(R.id.log_page_btn);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(character_choice_screen.this, Home_Activitey.class);
                startActivity(intent);
            }
        });
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}