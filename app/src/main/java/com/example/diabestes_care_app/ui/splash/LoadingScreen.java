package com.example.diabestes_care_app.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabestes_care_app.R;

/**
 * هذه الشاشة تُستخدم كشاشة تحميل أولية
 * وتُحدد ما إذا كان المستخدم شاهد مقدمة التطبيق (Splash) أم لا.
 */
public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // عرض تصميم شاشة تحميل مؤقتة (مثلاً: "لا يوجد إنترنت" أو شعار التطبيق)
        setContentView(R.layout.g_screen_no_internet_loading);
    }

    /**
     * دالة لفحص إذا سبق أن شاهد المستخدم شاشة المقدمة
     * @return true إذا شاهد المقدمة سابقًا، false إذا لم يشاهدها بعد
     */
    public boolean restorePrefData() {
        SharedPreferences pref = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);
    }

    /**
     * بمجرد بدء النشاط (onStart)، نتحقق مما إذا كان المستخدم شاهد المقدمة أم لا
     * ثم نوجّهه إلى الشاشة المناسبة.
     */
    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (restorePrefData()) {
                // إذا شاهد المقدمة من قبل → يذهب مباشرة لاختيار نوع الحساب
                startActivity(new Intent(this, CharacterChoiceScreen.class));
            } else {
                // إذا لم يشاهدها → يعرض شاشة المقدمة SplashScreen
                startActivity(new Intent(this, SplashScreen.class));
            }
        } catch (Exception e) {
            Log.e("LoadingScreen", "Error while navigating", e);
        } finally {
            finish(); // إغلاق شاشة التحميل بعد النقل
        }
    }
}
