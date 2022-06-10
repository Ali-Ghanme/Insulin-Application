package com.example.diabestes_care_app.Ui.Patient_all.Setting_P;


import static com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Home_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane.Diabats_Info;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane.Personal_Info;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Edit_Profile_P extends Basic_Activity {
    TabLayout tabLayout;
    ViewPager viewPager_subject;
    MainAdapter2 adapter;
    DatabaseReference myRef;
    String restoredText;
    TextView name;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_edit_profile_p);

        //============================Defines=======================================================
        tabLayout = findViewById(R.id.tab_layout_2);
        viewPager_subject = findViewById(R.id.viewpager_tab_2);
        name = findViewById(R.id.EP_doctor_name_p);
        imageView = findViewById(R.id.EP_Doctor_image_p);

        //============================username ShardPreference======================================
        SharedPreferences prefs = Edit_Profile_P.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //====================Adapter Configuration=================================================
        adapter = new MainAdapter2(getSupportFragmentManager());
        adapter.AddFragment(new Personal_Info(), "بيانات شخصية");
        adapter.AddFragment(new Diabats_Info(), "بيانات مرضية");

        //========================Set Adapter for the viewpager Configuration=======================
        viewPager_subject.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager_subject);
    }

    //=====================================Adapter method===========================================
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

    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String PatientImage = snapshot.child(restoredText).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                String PatientName = snapshot.child(restoredText).child("personal_info").child("name").getValue(String.class);
                Log.d("TAG", name + "/" + PatientImage);
                Glide.with(Edit_Profile_P.this).load(PatientImage).into(imageView);
                name.setText(PatientName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}