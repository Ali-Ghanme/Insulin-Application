package com.example.diabestes_care_app.Ui.Sing_In;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_In.Fragment.Doctor_Fragment;
import com.example.diabestes_care_app.Ui.Sing_In.Fragment.Patient_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Sing_In extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager_subject = findViewById(R.id.viewpager_tab);

        adapter = new MainAdapter(getSupportFragmentManager());


        adapter.AddFragment(new Patient_Fragment(), "مريض");
        adapter.AddFragment(new Doctor_Fragment(), "طبيب");

        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
    }

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