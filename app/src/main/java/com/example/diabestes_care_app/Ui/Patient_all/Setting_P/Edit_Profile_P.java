package com.example.diabestes_care_app.Ui.Patient_all.Setting_P;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_In.Fragment.Doctor_Fragment;
import com.example.diabestes_care_app.Ui.Sing_In.Fragment.Patient_Fragment;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.google.android.material.tabs.TabLayout;


public class Edit_Profile_P extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    Sing_In.MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_edit_profile_p);
        tabLayout = findViewById(R.id.tab_layout_2);
        viewPager_subject = findViewById(R.id.viewpager_tab_2);

        adapter = new Sing_In.MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Patient_Fragment(), "مريض");
        adapter.AddFragment(new Doctor_Fragment(), "طبيب");

        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
    }
}