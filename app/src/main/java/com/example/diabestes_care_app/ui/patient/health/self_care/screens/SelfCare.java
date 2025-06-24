package com.example.diabestes_care_app.ui.patient.health.self_care.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.patient.health.self_care.fragments.LifeStyleFragment;
import com.example.diabestes_care_app.ui.patient.health.self_care.fragments.InstructionFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SelfCare extends Basic_Activity {

    TabLayout tabLayout;
    ViewPager viewPager_subject;
    SelfCare.MainAdapter adapter;
    // Hallow this is Update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_fragment_self_care_section);

        tabLayout = findViewById(R.id.tab_layout_SS);
        viewPager_subject = findViewById(R.id.viewpager_tab_SS);

        //====================Adapter Configuration=================================================
        adapter = new SelfCare.MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new InstructionFragment(), "التعليمات");
        adapter.AddFragment(new LifeStyleFragment(), "حياة صحية");

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




