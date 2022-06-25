package com.example.diabestes_care_app.Ui.Splah_Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapters.ViewAdapter_Splash_Screen;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Splash_Screen_Modal;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_up_pages.character_choice_screen;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;


public class Splash_Screen_1 extends Basic_Activity {
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;
    TextView tvSkip;
    ViewPager viewPager;
    DotsIndicator dotsIndicator;
    ViewAdapter_Splash_Screen viewAdapterSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //========================make the activity on full screen==================================
        fullscreen();
        //=======================check if its opened before or not==================================
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), character_choice_screen.class);
            startActivity(mainActivity);
            finish();
        }
        //==================================define views============================================
        btnNext = findViewById(R.id.btn_next_Sp);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_button);
        tvSkip = findViewById(R.id.tv_skip);
        viewPager = findViewById(R.id.screen_view_pager_splash);
        dotsIndicator = findViewById(R.id.Splash_indicator);

        //=================================fill list screen=========================================
        final List<Splash_Screen_Modal> mList = new ArrayList<>();
        mList.add(new Splash_Screen_Modal(" أنسولين", "هو تطبيق للرعاية ومتابعة" + " مرضى السكري",  R.drawable.ic_splash_1));
        mList.add(new Splash_Screen_Modal("نخبة من الاطباء", "يمكنك استشارة نخبة من الأطباء\n" + " المختصين بمرض السكري",   R.drawable.ic_splash_2));
        mList.add(new Splash_Screen_Modal("نظم جرعاتك", "يمكنك وضع منبه للتذكير بجرعات\n" + " العلاج",  R.drawable.ic_splash_4));
        mList.add(new Splash_Screen_Modal("عناية ذاتية", "قسم كامل للتعليمات والانظمة الصحية \n" + " الخاصة بمرضى السكري\n"  ,  R.drawable.ic_splash_4));
        mList.add(new Splash_Screen_Modal("تقارير","" + "متابعة حالة المريض وتسجيل تقارير ورسوم بيانية" ,  R.drawable.ic_splash_1));
        mList.add(new Splash_Screen_Modal("تقارير","" + "التذكير بالفحوصات الدورية لمريض السكري" ,  R.drawable.ic_splash_1));
        //=============================== setup viewpager===========================================
        viewAdapterSplashScreen = new ViewAdapter_Splash_Screen(this, mList);
        viewPager.setAdapter(viewAdapterSplashScreen);
        dotsIndicator.setViewPager(viewPager);

        //===============================next button click Listener=================================
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPager.getCurrentItem();
                if (position < mList.size() | position == mList.size()) {
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if (position > mList.size() - 1) { // when we rech to the last screen
                    // TODO : show the Get Started Button and hide the indicator and the next button
                    loadLastScreen();
                }
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
                Intent mainActivity = new Intent(getApplicationContext(), character_choice_screen.class);
                startActivity(mainActivity);
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
        dotsIndicator.setVisibility(View.INVISIBLE);
        // =====================================setup animation======================================
        btnGetStarted.setAnimation(btnAnim);
    }
}