package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Consulation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.My_Patient_Fragment_D;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Consolation_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager_subject;
    Consolation_Fragment.MainAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consulation, container, false);

        tabLayout = view.findViewById(R.id.Consu_tab_layout_SR);
        viewPager_subject = view.findViewById(R.id.Consu_viewpager_tab_SR);

        //====================Adapter Configuration=================================================
        adapter = new Consolation_Fragment.MainAdapter(getChildFragmentManager());
        adapter.AddFragment(new General_Fragment(), "استشارات عامة");
        adapter.AddFragment(new Privet_Cons(), "استشاراتي");

        //========================Set Adapter for the viewpager Configuration=======================
        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
        return view;
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