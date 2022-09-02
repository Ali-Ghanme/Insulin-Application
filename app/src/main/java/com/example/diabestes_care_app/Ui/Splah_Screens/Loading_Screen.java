package com.example.diabestes_care_app.Ui.Splah_Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_up_pages.character_choice_screen;

public class Loading_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding_screen);
    }

    public boolean restorePrefData() {
        SharedPreferences pref = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //=======================check if its opened before or not==================================
        if (restorePrefData()) {
            Intent mainActivity = new Intent(this, character_choice_screen.class);
            startActivity(mainActivity);
            finish();
        }else {
            Intent intent = new Intent(Loading_Screen.this,Splash_Screen_1.class);
            startActivity(intent);
        }
    }
}