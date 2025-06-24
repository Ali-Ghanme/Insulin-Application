package com.example.diabestes_care_app.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.auth.Sing_In;
import com.example.diabestes_care_app.ui.auth.doctor.sing_up.Sing_Up_1_D;
import com.example.diabestes_care_app.ui.auth.patient.sing_up.PatientSignUpStep1Activity;
import com.example.diabestes_care_app.ui.doctor.HomeDoctorScreen;
import com.example.diabestes_care_app.ui.patient.HomePatientScreen;
import com.example.diabestes_care_app.viewmodel.splash.CharacterChoiceViewModel;

public class CharacterChoiceScreen extends Basic_Activity {

    private CharacterChoiceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen(); // اجعل الشاشة ملء الشاشة
        super.onCreate(savedInstanceState);
        setContentView(R.layout.g_screen_character_choice);

        // ==== ربط مكونات الواجهة ====
        Button btn_d = findViewById(R.id.doctor_btn);
        Button btn_p = findViewById(R.id.patient_btn);
        TextView log_in = findViewById(R.id.log_page_btn);

        // عند اختيار الطبيب، انتقل إلى شاشة التسجيل الخاصة بالطبيب
        btn_d.setOnClickListener(v -> startActivity(new Intent(this, Sing_Up_1_D.class)));

        // عند اختيار المريض، انتقل إلى شاشة التسجيل الخاصة بالمريض
        btn_p.setOnClickListener(v -> startActivity(new Intent(this, PatientSignUpStep1Activity.class)));

        // عند الضغط على تسجيل الدخول، افتح شاشة تسجيل الدخول
        log_in.setOnClickListener(v -> startActivity(new Intent(this, Sing_In.class)));

        // ==== إعداد ViewModel ====
        viewModel = new ViewModelProvider(this).get(CharacterChoiceViewModel.class);

        // استمع لتحديثات التنقل من ViewModel
        observeNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // تحقق من المستخدم المحفوظ مسبقًا وقرر التنقل المناسب
        viewModel.checkSavedUser();
    }

    /**
     * تراقب تغيرات التنقل في ViewModel وتقوم بفتح الشاشة المناسبة
     */
    private void observeNavigation() {
        viewModel.getNavigationTarget().observe(this, destination -> {
            if (destination == null) return;
            switch (destination) {
                case "patient":
                    startActivity(new Intent(this, HomePatientScreen.class));
                    finish(); // ننهي الشاشة الحالية لتجنب العودة إليها
                    break;
                case "doctor":
                    startActivity(new Intent(this, HomeDoctorScreen.class));
                    finish();
                    break;
            }
        });
    }
}
