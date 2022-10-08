package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Exams;

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
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class My_Exams_Fragment extends Fragment {

    TabLayout tabLayout_exams;
    ViewPager viewPager;
    My_Exams_Fragment.MainAdapter adapter;
    // Mohammed Siam
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my__exams_, container, false);

        tabLayout_exams = view.findViewById(R.id.exams_tab_layout_SR);
        viewPager  = view.findViewById(R.id.exams_viewpager_tab_SR);


        //====================Adapter Configuration=================================================
        adapter = new My_Exams_Fragment.MainAdapter(getChildFragmentManager());
        adapter.AddFragment(new Daily_Sugar(), "فحص يومي");
        adapter.AddFragment(new Dmonthly_Sugar(), "فحص دوري");

        //========================Set Adapter for the viewpager Configuration=======================
        viewPager.setAdapter(adapter);
        tabLayout_exams.setupWithViewPager(viewPager);


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
// Hallow this is Update