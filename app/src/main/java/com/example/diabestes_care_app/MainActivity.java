package com.example.diabestes_care_app;

import android.os.Bundle;

public class MainActivity extends Basic_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_fragment_main_dose);
    }
}
