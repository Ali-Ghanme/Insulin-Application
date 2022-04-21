package com.example.diabestes_care_app.Ui.Patient_all;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.Adapters.ViewAdapter_Home_P;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Doctor_List_Modal_P;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Fragment.Care_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Fragment.Chat_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Fragment.Home_Fragment;
import com.example.diabestes_care_app.Ui.Patient_all.Fragment.Profile_Fragment;

import java.util.ArrayList;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Home_Patient extends Basic_Activity {
    private static final String TAG = Home_Patient.class.getSimpleName();

    FragmentManager fragmentManager;
    AnimatedBottomBar animatedBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_home_p);

        animatedBottomBar = findViewById(R.id.animate_bottom);
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.nav_menu_home, true);
            fragmentManager = getSupportFragmentManager();
            Home_Fragment home_fragment = new Home_Fragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container,
                    home_fragment).commit();
        }
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