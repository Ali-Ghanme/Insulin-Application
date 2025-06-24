package com.example.diabestes_care_app.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.auth.doctor.fragments.login_doctor_fragment;
import com.example.diabestes_care_app.ui.auth.patient.fragment.logIn_patient_fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Sing_In extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    MainAdapter adapter;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_screen_log_in);

        //======================Pass Data===========================================================
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //======================Casting=============================================================
        tabLayout = findViewById(R.id.tab_layout);
        viewPager_subject = findViewById(R.id.viewpager_tab_2);

        //====================Adapter Configuration=================================================
        adapter = new MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new logIn_patient_fragment(), "مريض");
        adapter.AddFragment(new login_doctor_fragment(), "طبيب");

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