package com.example.diabestes_care_app.ui.auth.patient.sing_up;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.data.model.sing_up_p.PatientInfo;
import com.example.diabestes_care_app.viewmodel.auth.sing_up.PatientSignUp2ViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class PatientSignUpStep2Activity extends Basic_Activity {

    private EditText phoneNumber, email, password, confirmPassword;
    TextView city;
    private final String[] items = {"غزة", "رفح", "خانيونس", "ديرالبلح", "الوسطى", "الشمال"};
    private String patientUserName;

    private PatientSignUp2ViewModel patientViewModel;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_screen_sign_up_2);

        // ======================== ViewModel ========================
        patientViewModel = new ViewModelProvider(this).get(PatientSignUp2ViewModel.class);

        // ======================== Bind Views ========================
        phoneNumber = findViewById(R.id.Sp2_et_phone_P);
        email = findViewById(R.id.Sp2_et_email_P);
        password = findViewById(R.id.Sp2_et_Pass_P);
        confirmPassword = findViewById(R.id.Sp2_et_coPass_P);
        city = findViewById(R.id.Sp2_et_city_P);
        Button btnNext = findViewById(R.id.Sp2_bt_next_P);

        patientUserName = getIntent().getStringExtra("username");

        // ======================== City Selection ========================
        city.setOnClickListener(v -> showCityBottomSheet());

        // ======================== Password Visibility ========================
        setPasswordVisibilityToggle(password);
        setPasswordVisibilityToggle(confirmPassword);

        // ======================== Next Button ========================
        btnNext.setOnClickListener(v -> saveStep2());
    }

    private void saveStep2() {
        String phone = phoneNumber.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        String pass = password.getText().toString();
        String coPass = confirmPassword.getText().toString();
        String cityStr = city.getText().toString();

        if (patientViewModel.isAnyFieldEmpty(phone, emailStr, pass, coPass, cityStr)) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(coPass)) {
            Toast.makeText(this, "password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!patientViewModel.isValidEmail(emailStr)) {
            Toast.makeText(this, "Email is not correct Syntax", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 8) {
            Toast.makeText(this, "Password must be more than 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!patientViewModel.isValidPhone(phone)) {
            Toast.makeText(this, "Phone is not correct", Toast.LENGTH_SHORT).show();
            return;
        }

        PatientInfo info = new PatientInfo(phone, emailStr, pass, coPass, cityStr);
        patientViewModel.saveStep2Data(patientUserName, info, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PatientSignUpStep2Activity.this, PatientSignUpStep3Activity.class);
                intent.putExtra("username2", patientUserName);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCityBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.p_layout_bottom_sheet_main, findViewById(R.id.bottomSheetContier));
        ListView listView = view.findViewById(R.id.City_bottom_listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_listview, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            city.setText(items[position]);
            dialog.dismiss();
        });

        dialog.setContentView(view);
        dialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setPasswordVisibilityToggle(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_START = 0; // DrawableStart index
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (editText.getCompoundDrawables()[DRAWABLE_START] != null) {
                    int iconWidth = editText.getCompoundDrawables()[DRAWABLE_START].getBounds().width();
                    float touchX = event.getX();

                    if (touchX <= (editText.getPaddingStart() + iconWidth)) {
                        togglePasswordVisibility(editText);
                        return true;
                    }
                }
            }
            return false;
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void togglePasswordVisibility(EditText editText) {
        int inputType = editText.getInputType();
        if ((inputType & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            // إخفاء كلمة المرور
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // تغيير الأيقونة إلى ic_password_hide
            editText.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(R.drawable.ic_password_hide), // start
                    null, null, null);
        } else {
            // إظهار كلمة المرور
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // تغيير الأيقونة إلى ic_password_show
            editText.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(R.drawable.ic_password_show), // start
                    null, null, null);
        }
        // إعادة تعيين المؤشر في نهاية النص بعد التغيير
        editText.setSelection(editText.getText().length());
    }

    @Override
    public void onBackPressed() {
        patientViewModel.deletePatientNodeIfExists(patientUserName);
        super.onBackPressed();
    }
}
