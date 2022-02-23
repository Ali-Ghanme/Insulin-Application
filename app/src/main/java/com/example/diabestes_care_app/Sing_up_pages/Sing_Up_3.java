package com.example.diabestes_care_app.Sing_up_pages;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Sing_Up_3 extends Basic_Activity {
    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up3);
        //====================================Define Checkbox===============================
        btn_next = findViewById(R.id.btn_next_3);
        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sing_Up_3.class);
                startActivity(intent);
            }
        });


    }
}