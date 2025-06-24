package com.example.diabestes_care_app.ui.doctor;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;

import com.example.diabestes_care_app.ui.doctor.chat.fragments.ChatDoctorFragment;
import com.example.diabestes_care_app.ui.doctor.following.fragments.following.MyPatientFragment;
import com.example.diabestes_care_app.ui.doctor.home.fragments.HomeDoctorFragment;
import com.example.diabestes_care_app.ui.doctor.profile.fragments.ProfileFragment;
import com.example.diabestes_care_app.ui.patient.HomePatientScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeDoctorScreen extends Basic_Activity {

    private static final String TAG = HomePatientScreen.class.getSimpleName();
    FragmentManager fragmentManager;
    AnimatedBottomBar animatedBottomBar;
    String DoctorUsername;
    DatabaseReference myRef;
    public static final String MyPREFERENCES_D = "D_Username";
    Dialog dialog;
    Button Continue;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.d_activity_home);
        //============================Defines=======================================================
        animatedBottomBar = findViewById(R.id.animate_bottom_d);

        //============================Animation=====================================================
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.nav_menu_home_D, true);
            fragmentManager = getSupportFragmentManager();
            HomeDoctorFragment home_fragment = new HomeDoctorFragment();
//             Add Home Fragment as the default Fragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container_d, home_fragment).commit();
        }
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(HomeDoctorScreen.this);
        dialog.setContentView(R.layout.g_dialog_offline);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Continue = dialog.findViewById(R.id.Continue);

        //============================Shared Preference=============================================
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Firebase======================================================
        myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(DoctorUsername);


        online_status_all_users.setValue("online");
        online_status_all_users.onDisconnect().setValue("offline");

        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String snooping_status = dataSnapshot.getValue(String.class);
                if (snooping_status.contentEquals("offline")) {
                    Log.e("TAG", DoctorUsername + snooping_status);
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Continue.setOnClickListener(v -> dialog.dismiss());
        //============================BottomNavigation Transaction==================================
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.nav_menu_home_D:
                        fragment = new HomeDoctorFragment();
                        break;
                    case R.id.nav_menu_Following_D:
                        fragment = new MyPatientFragment();
                        break;
                    case R.id.nav_menu_chat_D:
                        fragment = new ChatDoctorFragment();
                        break;
                    case R.id.nav_menu_user_profile_D:
                        fragment = new ProfileFragment();
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
                Toast.makeText(HomeDoctorScreen.this, " أنت بلفعل في واجهة " + tab.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
