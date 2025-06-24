package com.example.diabestes_care_app.ui.patient.health.reports.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapter.Reports_Adapter;
import com.example.diabestes_care_app.data.model.Reports_Model;
import com.example.diabestes_care_app.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ReportsTabsFragment extends Fragment {
    // Mohammed Siam
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    ReportsTabsFragment.MainAdapter adapter;
    Reports_Adapter reports_adapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Reports_Model> list;
    String PatientUsername;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports_, container, false);

        tabLayout = view.findViewById(R.id.Repo_tab_layout_SR);
        viewPager_subject = view.findViewById(R.id.Repo_viewpager_tab_SR);

        //====================Adapter Configuration=================================================
        adapter = new ReportsTabsFragment.MainAdapter(getChildFragmentManager());
        adapter.AddFragment(new DailyReportFragment(), "تقارير يومية");
        adapter.AddFragment(new PeriodicReportFragment(), "تقارير دورية");
        //========================Set Adapter for the viewpager Configuration=======================
        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);

        //********************************************************************

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