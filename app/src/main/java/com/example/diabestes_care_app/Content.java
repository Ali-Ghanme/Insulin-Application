package com.example.diabestes_care_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapters.ViewAdapter_Content;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.SelfCare_Model;

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
        mList.add(new SelfCare_Model("مرحبا راح يكون المحتوى هان ممكن نحطو في ملفات خارجية ونستدعي استدعاء بدل ما نكتبو هان راح الاقي الطريقة ان شاء الله D:1", R.drawable.ic_splash_1));
        mList.add(new SelfCare_Model("مرحبا راح يكون المحتوى هان ممكن نحطو في ملفات خارجية ونستدعي استدعاء بدل ما نكتبو هان راح الاقي الطريقة ان شاء الله D:2", R.drawable.ic_splash_1));
        mList.add(new SelfCare_Model("مرحبا راح يكون المحتوى هان ممكن نحطو في ملفات خارجية ونستدعي استدعاء بدل ما نكتبو هان راح الاقي الطريقة ان شاء الله D:3", R.drawable.ic_splash_1));
        mList.add(new SelfCare_Model("مرحبا راح يكون المحتوى هان ممكن نحطو في ملفات خارجية ونستدعي استدعاء بدل ما نكتبو هان راح الاقي الطريقة ان شاء الله D:4", R.drawable.ic_splash_1));

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