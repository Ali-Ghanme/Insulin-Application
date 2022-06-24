package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapters.ViewAdapter_Content;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.SelfCare_Model;
import com.example.diabestes_care_app.R;

import java.util.ArrayList;
import java.util.List;

public class Content extends Basic_Activity {

    ViewPager viewPager;
    ViewAdapter_Content viewAdapter_content;
    ImageView btnNext;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        //==================================define views===============================================
        viewPager = findViewById(R.id.view_pager_content);
        btnNext = findViewById(R.id.content_next_btn);

        //=================================fill list screen=========================================
        final List<SelfCare_Model> mList = new ArrayList<>();

        mList.add(new SelfCare_Model(R.string.Gabobet_sokar, R.drawable.ic_splash_1 , "غيبوبة السكري"));

        //=============================== setup viewpager===========================================
        viewAdapter_content = new ViewAdapter_Content(this, mList);
        viewPager.setAdapter(viewAdapter_content);

        //===============================next button click Listener=================================
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPager.getCurrentItem();
                if (position < mList.size() | position == mList.size()) {
                    position++;
                    viewPager.setCurrentItem(position);
                }
            }
        });
    }
}