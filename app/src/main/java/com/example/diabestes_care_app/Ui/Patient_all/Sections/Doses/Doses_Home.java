package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses;

import android.os.Bundle;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class Doses_Home extends Basic_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doses_home);
    }
}