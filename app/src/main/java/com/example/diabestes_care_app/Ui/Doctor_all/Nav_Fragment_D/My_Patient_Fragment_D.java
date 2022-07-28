package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

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
import com.example.diabestes_care_app.Ui.Doctor_all.Secation.Consulation.Consolation_Fragment;
import com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.Follow_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class My_Patient_Fragment_D extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    My_Patient_Fragment_D.MainAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__patient___d, container, false);

        tabLayout = view.findViewById(R.id.My_patient_tab_layout_SR);
        viewPager_subject = view.findViewById(R.id.My_patient_viewpager_tab_SR);

        //====================Adapter Configuration=================================================
        adapter = new My_Patient_Fragment_D.MainAdapter(getChildFragmentManager());
        adapter.AddFragment(new Follow_Fragment(), "أتابع");
        adapter.AddFragment(new Consolation_Fragment(), "الاستشارات ");

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
