package com.example.diabestes_care_app.ui.doctor.following.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.doctor.following.fragments.following.DailyReportFragment;
import com.example.diabestes_care_app.ui.doctor.following.fragments.following.PeriodicReportFragment;
import com.example.diabestes_care_app.ui.doctor.following.fragments.following.PatientInfoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class PatentReportInfoScreen extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    PatentReportInfoScreen.MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_patient_report);

        tabLayout = findViewById(R.id.Patient_reports_tab_D);
        viewPager_subject = findViewById(R.id.Patient_reports_viewpager_tab_D);

        //====================Adapter Configuration=================================================
        adapter = new PatentReportInfoScreen.MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new PatientInfoFragment(), "م.المريض");
        adapter.AddFragment(new DailyReportFragment(), "ف.اليومية");
        adapter.AddFragment(new PeriodicReportFragment(), "ف.الدورية");

        //========================Set Adapter for the viewpager Configuration=======================
        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
    }

    //=====================================Adapter method===========================================
    public static class MainAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        //Create Constructor
        public void AddFragment(Fragment fragment, String s) {
            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArrayList.get(position);
        }
    }
}
