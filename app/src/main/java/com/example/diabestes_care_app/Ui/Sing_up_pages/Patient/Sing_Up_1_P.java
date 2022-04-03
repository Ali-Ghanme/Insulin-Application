package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Sing_Up_1_P extends Basic_Activity {

    Button btn_next_S;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_1_p);
        btn_next_S = findViewById(R.id.btn_next_S);
        //====================================Define variables===============================
        btn_next_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sing_Up_2_P.class);
                startActivity(intent);
            }
        });
    }
}
