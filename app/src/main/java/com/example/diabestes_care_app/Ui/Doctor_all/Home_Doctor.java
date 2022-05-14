package com.example.diabestes_care_app.Ui.Doctor_all;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Home_Doctor extends Basic_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_home_d);
    }
}