package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Patient_Files extends Basic_Activity {

    TabLayout tabLayout;
    ViewPager viewPager_subject;
    Patient_Files.MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        tabLayout = findViewById(R.id.tab_layout_SR);
        viewPager_subject = findViewById(R.id.viewpager_tab_SR);

        //====================Adapter Configuration=================================================
        adapter = new Patient_Files.MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Reports_Fragment(), "التقارير");
        adapter.AddFragment(new Graphs_Fragment(), "رسومات بيانية");
        adapter.AddFragment(new My_Exams_Fragment(), "فحوصاتي");

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
