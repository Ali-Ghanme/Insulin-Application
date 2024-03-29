package com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;


public final class AlarmLandingPageActivity extends Basic_Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fullscreen();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_landing_page);

    }

    public static Intent launchIntent(Context context) {
        final Intent i = new Intent(context, AlarmLandingPageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

}
