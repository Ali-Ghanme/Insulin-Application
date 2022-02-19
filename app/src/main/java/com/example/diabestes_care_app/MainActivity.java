package com.example.diabestes_care_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.diabestes_care_app.Adapters.ViewAdapter;
import com.example.diabestes_care_app.Models.ScreenItem;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    DotsIndicator dotsIndicator;
    ViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//
//        final List<ScreenItem> mList = new ArrayList<>();
//        mList.add(new ScreenItem(" اصحاء", "تطبيق  هو تطبيق للرعاية" + " بمرضى السكري", R.drawable.ic_splash_1));
//        mList.add(new ScreenItem("نخبة من الاطباء", "يمكنك استشارة نخبة من الأطباء\n" + " المختصين بمرض السكري", R.drawable.ic_splash_2));
//        mList.add(new ScreenItem("نظم جرعاتك", "يمكنك وضع منبه للتذكير بجرعات\n" + " العلاج يمكنك اختيار نوع العلاج ", R.drawable.ic_splash_1));
//        viewPager = findViewById(R.id.view_pager);
//        dotsIndicator = findViewById(R.id.dots_indicator);
//        viewAdapter = new ViewAdapter(this, mList);
//        viewPager.setAdapter(viewAdapter);
//        dotsIndicator.setViewPager(viewPager);
    }
}