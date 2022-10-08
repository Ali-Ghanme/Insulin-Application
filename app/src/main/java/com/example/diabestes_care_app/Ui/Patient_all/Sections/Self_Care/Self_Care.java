package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.SelfCare.Life_Style_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Self_Care extends Basic_Activity {

    TabLayout tabLayout;
    ViewPager viewPager_subject;
    Self_Care.MainAdapter adapter;
    // Hallow this is Update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_care);

        tabLayout = findViewById(R.id.tab_layout_SS);
        viewPager_subject = findViewById(R.id.viewpager_tab_SS);

        //====================Adapter Configuration=================================================
        adapter = new Self_Care.MainAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Instruction_Fragment(), "التعليمات");
        adapter.AddFragment(new Life_Style_Fragment(), "حياة صحية");

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




