package com.example.diabestes_care_app.ui.patient;

import static com.example.diabestes_care_app.ui.auth.patient.fragment.logIn_patient_fragment.MyPREFERENCES_P;
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
import com.example.diabestes_care_app.ui.patient.chat.fragments.ChatFragment;
import com.example.diabestes_care_app.ui.patient.health.HealthFragment;
import com.example.diabestes_care_app.ui.patient.home.fragments.HomeFragment;
import com.example.diabestes_care_app.ui.patient.profile.fragments.ProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomePatientScreen extends Basic_Activity {
    DatabaseReference myRef;
    private static final String TAG = HomePatientScreen.class.getSimpleName();
    FragmentManager fragmentManager;
    AnimatedBottomBar animatedBottomBar;
    // Patient Username TextView
    String PatientUsername;
    Dialog dialog;
    Button Continue;

    // Hallow this is Update
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.p_screen_home);

        //============================Defines=======================================================
        animatedBottomBar = findViewById(R.id.animate_bottom);
        //============================Animation=====================================================
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.nav_menu_home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment home_fragment = new HomeFragment();
//             Add Home Fragment as the default Fragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container, home_fragment).commit();
        }
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(HomePatientScreen.this);
        dialog.setContentView(R.layout.g_dialog_offline);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Continue = dialog.findViewById(R.id.Continue);
        //============================Get Patient Username===========================================
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //============================Firebase======================================================
        myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(PatientUsername);

        //============================Get User Status ==============================================
        online_status_all_users.setValue("online");
        online_status_all_users.onDisconnect().setValue("offline");
        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String snooping_status = dataSnapshot.getValue(String.class);
                //mario should decide what to do with linker's snooping status here e.g.
                assert snooping_status != null;
                if (snooping_status.contentEquals("offline")) {
                    if (!HomePatientScreen.this.isFinishing()) {
                        dialog.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Continue.setOnClickListener(v -> dialog.dismiss());
        //============================BottomNavigation Transaction==================================
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.nav_menu_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_menu_my_heartbeat:
                        fragment = new HealthFragment();
                        break;
                    case R.id.nav_menu_chat_:
                        fragment = new ChatFragment();
                        break;
                    case R.id.nav_menu_user_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Log.e(TAG, "Error in Creating Fragment");
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {
                Toast.makeText(HomePatientScreen.this, " أنت بلفعل في واجهة " + tab.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}