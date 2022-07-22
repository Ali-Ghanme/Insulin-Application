package com.example.diabestes_care_app.Ui.Doctor_all;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Chat_Fragment_D;
import com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D;
import com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.My_Patient_Fragment_D;
import com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Profile_Fragment_D;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Home_Doctor extends Basic_Activity {

    private static final String TAG = Home_Patient.class.getSimpleName();
    FragmentManager fragmentManager;
    AnimatedBottomBar animatedBottomBar;
    String DoctorUsername;
    DatabaseReference myRef;
    public static final String MyPREFERENCES_D = "D_Username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_home_d);
        //============================Defines=======================================================
        animatedBottomBar = findViewById(R.id.animate_bottom_d);

        //============================Animation=====================================================
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.nav_menu_home_D, true);
            fragmentManager = getSupportFragmentManager();
            Home_Fragment_D home_fragment = new Home_Fragment_D();
//             Add Home Fragment as the default Fragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container_d, home_fragment).commit();
        }


        //============================Firebase======================================================
        myRef = FirebaseDatabase.getInstance().getReference();

        //============================Get Doctor Username===========================================
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //say your realtime database has the child `online_statuses`
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses");

        //on each user's device when connected they should indicate e.g. `linker` should tell everyone he's snooping around
        online_status_all_users.child(DoctorUsername).setValue("online");
        //also when he's not doing any snooping or if snooping goes bad he should also tell
        online_status_all_users.child(DoctorUsername).onDisconnect().setValue("offline");

        //    DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses");

        online_status_all_users.child(DoctorUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String snooping_status = dataSnapshot.getValue(String.class);
                //mario should decide what to do with linker's snooping status here e.g.
                if (snooping_status.contentEquals("online")) {
                    Log.e("TAG", DoctorUsername + snooping_status);
                } else {
                    Toast.makeText(Home_Doctor.this, "User Offline", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //============================BottomNavigation Transaction==================================
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.nav_menu_home_D:
                        fragment = new Home_Fragment_D();
                        break;
                    case R.id.nav_menu_Following_D:
                        fragment = new My_Patient_Fragment_D();
                        break;
                    case R.id.nav_menu_chat_D:
                        fragment = new Chat_Fragment_D();
                        break;
                    case R.id.nav_menu_user_profile_D:
                        fragment = new Profile_Fragment_D();
                        break;
                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_d, fragment).commit();
                } else {
                    Log.e(TAG, "Error in Creating Fragment");
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {
                Toast.makeText(Home_Doctor.this, " أنت بلفعل في واجهة " + tab.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
