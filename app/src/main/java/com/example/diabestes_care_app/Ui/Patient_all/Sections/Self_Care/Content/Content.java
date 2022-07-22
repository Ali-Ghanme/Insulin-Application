package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapters.ViewAdapter_Content;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.SelfCare_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Self_Care;

import java.util.ArrayList;
import java.util.List;

public class Content extends Basic_Activity {

    ViewPager viewPager;
    ViewAdapter_Content viewAdapter_content;
    ImageView btnNext, btnBack;
    Button end;
    int position = 0;
    Dialog dialog;
    Animation btnAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String[] items = {"عن مرض السكري", "أنواع مرض السكري", "مضاعفات مرض السكري", "الفطور في رمضان", "السحور في رمضان", "ما هي غيبوبة السكر",
                "اعراض مبكرة لمرض السكري", "التعامل مع حالات اغماء السكري", "جرعات الانسولين وكيفية اخدها"};
        //==================================define views===============================================
        viewPager = findViewById(R.id.view_pager_content);
        btnNext = findViewById(R.id.content_next_btn);
        btnBack = findViewById(R.id.content_back_btn);
        end = findViewById(R.id.btn_the_end);
        //=================================fill list screen=========================================
        final List<SelfCare_Model> mList = new ArrayList<>();

        mList.add(new SelfCare_Model(R.string.عن_المرض, R.drawable.ic_splash_1, items[0]));
        mList.add(new SelfCare_Model(R.string.أنواع_المرض, R.drawable.ic_splash_1, items[1]));
        mList.add(new SelfCare_Model(R.string.مضاعفات_المرض, R.drawable.ic_splash_1, items[2]));
        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1, items[3]));
        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1, items[4]));
        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1, items[5]));
        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1, items[6]));
        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1, items[7]));
        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1, items[8]));


        //=============================== setup viewpager===========================================
        viewAdapter_content = new ViewAdapter_Content(this, mList);
        viewPager.setAdapter(viewAdapter_content);

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(Content.this);
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        ;
        //===============================next button click Listener=================================
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Content.this, Self_Care.class);
                startActivity(intent);
                finish();
            }
        });

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
                    Toast.makeText(Content.this, "The End", Toast.LENGTH_SHORT).show();
                    loadLastScreen();
                    dialog.show();
                }
            }
        });

    }

    //==========show the Get Started Button and hide the indicator and the next button==============
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        end.setVisibility(View.VISIBLE);
        // =====================================setup animation======================================
        end.setAnimation(btnAnim);
    }
}