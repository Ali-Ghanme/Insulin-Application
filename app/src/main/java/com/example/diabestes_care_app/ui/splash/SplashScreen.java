package com.example.diabestes_care_app.ui.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Adapter.ViewAdapter_Splash_Screen;
import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.viewmodel.splash.SplashScreenViewModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends Basic_Activity {

    // عناصر الواجهة
    private Button btnNext, btnGetStarted;
    private TextView tvSkip;
    private ViewPager viewPager;
    private DotsIndicator dotsIndicator;
    private SplashScreenViewModel viewModel;
    private Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen(); // جعل الشاشة بوضع ملء الشاشة
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_screen_splash);

        // ربط العناصر من التصميم
        btnNext = findViewById(R.id.btn_next_Sp);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tvSkip = findViewById(R.id.tv_skip);
        viewPager = findViewById(R.id.screen_view_pager_splash);
        dotsIndicator = findViewById(R.id.Splash_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_button);

        // تهيئة ViewModel
        viewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);

        // مراقبة التغيرات في قائمة الشاشات وعرضها في الـ ViewPager
        viewModel.getSplashPages().observe(this, pages -> {
            ViewAdapter_Splash_Screen adapter = new ViewAdapter_Splash_Screen(this, pages);
            viewPager.setAdapter(adapter);
            dotsIndicator.setViewPager(viewPager); // ربط مؤشر النقاط مع ViewPager
        });

        // عند الضغط على زر "التالي"
        btnNext.setOnClickListener(v -> {
            int nextItem = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(nextItem); // الانتقال للصفحة التالية
        });

        // التحقق من الصفحة الحالية لتحديد ما إذا كنا في الصفحة الأخيرة
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewModel.onPageChanged(position);
            }
        });

        // إظهار أو إخفاء الأزرار بناءً على الصفحة الحالية
        viewModel.getIsLastPage().observe(this, isLast -> {
            if (isLast) {
                btnNext.setVisibility(View.INVISIBLE);
                btnGetStarted.setVisibility(View.VISIBLE);
                tvSkip.setVisibility(View.INVISIBLE);
                dotsIndicator.setVisibility(View.INVISIBLE);
                btnGetStarted.setAnimation(btnAnim); // تشغيل الحركة عند الظهور
            } else {
                btnNext.setVisibility(View.VISIBLE);
                btnGetStarted.setVisibility(View.INVISIBLE);
                tvSkip.setVisibility(View.VISIBLE);
                dotsIndicator.setVisibility(View.VISIBLE);
            }
        });

        // عند الضغط على "ابدأ" أو "تخطي"
        View.OnClickListener finishIntroListener = v -> {
            viewModel.saveIntroSeen(); // حفظ أن المقدمة عُرضت
            startActivity(new Intent(this, CharacterChoiceScreen.class)); // الانتقال للصفحة التالية
            finish(); // إنهاء الشاشة الحالية
        };
        btnGetStarted.setOnClickListener(finishIntroListener);
        tvSkip.setOnClickListener(finishIntroListener);
    }
}
