package com.example.diabestes_care_app.Ui.Patient_all.Setting_P;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Switch;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
@SuppressLint("UseSwitchCompatOrMaterialCode")
public class notification extends Basic_Activity {
    Switch DarkMode_cont ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
  // حذف هاي الصفحة
    }
}
// Hallow this is Update