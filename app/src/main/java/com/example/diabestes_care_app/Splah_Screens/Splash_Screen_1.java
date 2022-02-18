package com.example.diabestes_care_app.Splah_Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.diabestes_care_app.Adapters.Splash_Adapter;
import com.example.diabestes_care_app.MainActivity;
import com.example.diabestes_care_app.Models.ScreenItem;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Sing_up_pages.character_choice_screen;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Splash_Screen_1 extends AppCompatActivity {

    private ViewPager screenPager;
    Splash_Adapter viewpagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);
        //========================make the activity on full screen==================================
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //=======================check if its opened before or not==================================
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), character_choice_screen.class);
            startActivity(mainActivity);
            finish();
        }
        setContentView(R.layout.activity_splash_screen1);
        //==================================hide the action bar=====================================
//        Objects.requireNonNull(getSupportActionBar()).hide();

        //==================================ini views===============================================
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_button);
        tvSkip = findViewById(R.id.tv_skip);

        //=================================fill list screen=========================================
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(" اصحاء", "تطبيق  هو تطبيق للرعاية" + " بمرضى السكري", R.drawable.ic_splash_1));
        mList.add(new ScreenItem("نخبة من الاطباء", "يمكنك استشارة نخبة من الأطباء\n" + " المختصين بمرض السكري", R.drawable.ic_splash_2));
        mList.add(new ScreenItem("نظم جرعاتك", "يمكنك وضع منبه للتذكير بجرعات\n" + " العلاج يمكنك اختيار نوع العلاج ", R.drawable.ic_splash_1));

        //=============================== setup viewpager===========================================
        screenPager = findViewById(R.id.screen_view_pager_splash);
        viewpagerAdapter = new Splash_Adapter(this, mList);
        screenPager.setAdapter(viewpagerAdapter);

        //===============================setup Tab Layout with viewpager============================
        tabIndicator.setupWithViewPager(screenPager);

        //===============================next button click Listener=================================
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size() - 1) { // when we rech to the last screen
                    // TODO : show the Get Started Button and hide the indicator and the next button
                    loadLastScreen();
                }
            }
        });

        //============================Tab layout add change listener================================
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //==========================Get Started button click listener===============================
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent mainActivity = new Intent(getApplicationContext(), character_choice_screen.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();
            }
        });

        //===============================skip button click listener=================================
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();
    }

    //==========show the Get Started Button and hide the indicator and the next button==============
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // =====================================setup animation======================================
        btnGetStarted.setAnimation(btnAnim);
    }
}