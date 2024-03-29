package com.example.diabestes_care_app.Ui.Sing_In.RestPassword_D;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;

public class RestPassword4_d extends Basic_Activity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password4);
        button = findViewById(R.id.sing_in_pass);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(RestPassword4_d.this, Sing_In.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}