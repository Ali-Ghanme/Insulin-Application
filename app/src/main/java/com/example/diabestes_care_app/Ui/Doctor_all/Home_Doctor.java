package com.example.diabestes_care_app.Ui.Doctor_all;

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
import com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Profile_Fragment_D;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class Home_Doctor extends Basic_Activity {

    private static final String TAG = Home_Patient.class.getSimpleName();
    FragmentManager fragmentManager;
    AnimatedBottomBar animatedBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_home_d);
        //============================Defines=======================================================
        animatedBottomBar = findViewById(R.id.animate_bottom_d);

        //============================Animation=====================================================
        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.nav_menu_home, true);
            fragmentManager = getSupportFragmentManager();
            Home_Fragment_D home_fragment = new Home_Fragment_D();
//             Add Home Fragment as the default Fragment
            fragmentManager.beginTransaction().replace(R.id.fragment_container_d, home_fragment).commit();
        }
        //============================BottomNavigation Transaction==================================
        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.nav_menu_home:
                        fragment = new Home_Fragment_D();
                        break;
                    case R.id.nav_menu_chat_:
                        fragment = new Chat_Fragment_D();
                        break;
                    case R.id.nav_menu_user_profile:
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
// Hallow this is update
