package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapter.ViewAdapter_Content;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.SelfCare_Model;
import com.example.diabestes_care_app.R;

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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String[] items = {"عن المرض", "أنواع المرض", "مضاعفات المرض", "غيبوبة السكر", "جرعات الأنسولين", "أعراض مرض السكري", "تعليمات"};
        //==================================define views===============================================
        viewPager = findViewById(R.id.view_pager_content);
        btnBack = findViewById(R.id.content_back_btn);
        //=================================fill list screen=========================================
        final List<SelfCare_Model> mList = new ArrayList<>();

        mList.add(new SelfCare_Model(R.string.عن_المرض, items[0]));
        mList.add(new SelfCare_Model(R.string.أنواع_المرض, items[1]));
        mList.add(new SelfCare_Model(R.string.مضاعفات_مرض_السكري, items[2]));
        mList.add(new SelfCare_Model(R.string.غيبوبة_السكر, items[3]));
        mList.add(new SelfCare_Model(R.string.جرعات_الأنسولين, items[4]));
        mList.add(new SelfCare_Model(R.string.أعراض_مرض_السكري, items[5]));
        mList.add(new SelfCare_Model(R.string.تعليمات, items[6]));

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

        //===============================next button click Listener=================================
        btnBack.setOnClickListener(v -> onBackPressed());
//        end.setOnClickListener(v -> {
//            Intent intent = new Intent(Content.this, Self_Care.class);
//            startActivity(intent);
//            finish();
//        });
//        btnNext.setOnClickListener(v -> {
//            position = viewPager.getCurrentItem();
//            if (position < mList.size() | position == mList.size()) {
//                position++;
//                viewPager.setCurrentItem(position);
//            }
//            if (position > mList.size() - 1) { // when we rech to the last screen
//                // TODO : show the Get Started Button and hide the indicator and the next button
//                Toast.makeText(Content.this, "The End", Toast.LENGTH_SHORT).show();
//                loadLastScreen();
//            }
//        });

    }

//    //==========show the Get Started Button and hide the indicator and the next button==============
//    private void loadLastScreen() {
//        btnNext.setVisibility(View.INVISIBLE);
//        end.setVisibility(View.VISIBLE);
//        // =====================================setup animation======================================
//        end.setAnimation(btnAnim);
//    }
}