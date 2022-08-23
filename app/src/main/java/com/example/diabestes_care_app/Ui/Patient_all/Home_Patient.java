package com.example.diabestes_care_app.Ui.Patient_all;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Care_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Chat_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Home_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Profile_Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Home_Patient extends Basic_Activity {
    DatabaseReference myRef;
    private static final String TAG = Home_Patient.class.getSimpleName();
    FragmentManager fragmentManager;
    AnimatedBottomBar animatedBottomBar;
    // Patient Username TextView
    String PatientUsername;
    Dialog dialog;
    Button Continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_home_p);

        //============================Defines=======================================================
        animatedBottomBar = findViewById(R.id.animate_bottom);
        //============================Animation=====================================================
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.nav_menu_home, true);
            fragmentManager = getSupportFragmentManager();
            Home_Fragment home_fragment = new Home_Fragment();
//             Add Home Fragment as the default Fragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container, home_fragment).commit();
        }
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(Home_Patient.this);
        dialog.setContentView(R.layout.offline_dialog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Continue = dialog.findViewById(R.id.Continue);

        //============================Firebase======================================================
        myRef = FirebaseDatabase.getInstance().getReference();
        //============================Get Patient Username===========================================
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //============================Get User Status ==============================================
        //say your realtime database has the child `online_statuses`
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(PatientUsername);

        //on each user's device when connected they should indicate e.g. `linker` should tell everyone he's snooping around
        online_status_all_users.setValue("online");

        //also when he's not doing any snooping or if snooping goes bad he should also tell
        online_status_all_users.onDisconnect().setValue("offline");

        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String snooping_status = dataSnapshot.getValue(String.class);
                //mario should decide what to do with linker's snooping status here e.g.
                if (snooping_status.contentEquals("online")) {
                    //tell linker to stop doing sh*t
                    Toast.makeText(Home_Patient.this, snooping_status, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", snooping_status);
                } else {
                    //tell linker to do a lot of sh****t
                    Toast.makeText(Home_Patient.this, "All Doctors Offline", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", snooping_status);
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //============================BottomNavigation Transaction==================================
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.nav_menu_home:
                        fragment = new Home_Fragment();
                        break;
                    case R.id.nav_menu_my_heartbeat:
                        fragment = new Care_Fragment();
                        break;
                    case R.id.nav_menu_chat_:
                        fragment = new Chat_Fragment();
                        break;
                    case R.id.nav_menu_user_profile:
                        fragment = new Profile_Fragment();
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
                Toast.makeText(Home_Patient.this, " أنت بلفعل في واجهة " + tab.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}