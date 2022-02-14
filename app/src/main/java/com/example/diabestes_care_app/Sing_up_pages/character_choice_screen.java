package com.example.diabestes_care_app.Sing_up_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.example.diabestes_care_app.R;

public class character_choice_screen extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choice_screen);
        btn = findViewById(R.id.log_in_text_button);
        btn.getBackground().setAlpha(128);  // 50% transparent
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}