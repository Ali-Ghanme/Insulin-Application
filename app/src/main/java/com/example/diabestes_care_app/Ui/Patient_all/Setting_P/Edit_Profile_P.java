package com.example.diabestes_care_app.Ui.Patient_all.Setting_P;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane.Diabats_Info;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane.Personal_Info;
import com.example.diabestes_care_app.Ui.Sing_In.Fragment.Doctor_Fragment;
import com.example.diabestes_care_app.Ui.Sing_In.Fragment.Patient_Fragment;
import com.example.diabestes_care_app.Ui.Sing_In.Sing_In;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Edit_Profile_P extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    MainAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_edit_profile_p);

        tabLayout = findViewById(R.id.tab_layout_2);
        viewPager_subject = findViewById(R.id.viewpager_tab_2);

        adapter = new MainAdapter2(getSupportFragmentManager());
        adapter.AddFragment(new Personal_Info(), "بيانات شخصية");
        adapter.AddFragment(new Diabats_Info(), "بيانات مرضية");

        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
    }


    public static class MainAdapter2 extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        //Create Constructor
        public void AddFragment(Fragment fragment, String s) {
            fragmentArrayList.add(fragment);
            stringArrayList.add(s);
        }

        public MainAdapter2(@NonNull FragmentManager fm) {
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