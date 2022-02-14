package com.example.diabestes_care_app.Splah_Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Sing_up_pages.character_choice_screen;

public class Splash_Screen_3 extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen3);
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //this code for the splash screen what to do after finish it
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(Splash_Screen_3.this, character_choice_screen.class);
                startActivity(intent2);
            }
        }, SPLASH_SCREEN);
    }
}