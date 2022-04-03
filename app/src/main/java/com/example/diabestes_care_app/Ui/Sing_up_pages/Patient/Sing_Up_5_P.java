package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.example.diabestes_care_app.databinding.ActivityHomeDBinding;

public class Sing_Up_5_P extends Basic_Activity {
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_5_p);
        //====================================Define Checkbox===============================
        btn_next = findViewById(R.id.btn_next_3);
        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sing_Up_5_P.this, Sing_In.class);
                startActivity(intent);
            }
        });
    }
}