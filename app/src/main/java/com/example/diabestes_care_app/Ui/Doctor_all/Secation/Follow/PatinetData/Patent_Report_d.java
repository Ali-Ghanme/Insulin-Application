package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment.Daily_Report_Fragment;
import com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment.Dwree_Report_Fragment;
import com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment.Patient_info_Fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class Patent_Report_d extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    Patent_Report_d.MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patinet_report_d);

        tabLayout = findViewById(R.id.Patient_reports_tab_D);
        viewPager_subject = findViewById(R.id.Patient_reports_viewpager_tab_D);

        //====================Adapter Configuration=================================================
        adapter = new Patent_Report_d.MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Patient_info_Fragment(), "م.المريض");
        adapter.AddFragment(new Daily_Report_Fragment(), "ف.اليومية");
        adapter.AddFragment(new Dwree_Report_Fragment(), "ف.الدورية");

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
