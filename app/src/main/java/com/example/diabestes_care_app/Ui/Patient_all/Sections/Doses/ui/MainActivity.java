package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.ui;

import android.os.Bundle;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;

public class MainActivity extends Basic_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dose);
    }
}
