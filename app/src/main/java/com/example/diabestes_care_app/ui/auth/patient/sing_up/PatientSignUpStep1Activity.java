package com.example.diabestes_care_app.ui.auth.patient.sing_up;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.viewmodel.auth.sing_up.PatientSignUp1ViewModel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class PatientSignUpStep1Activity extends Basic_Activity {
    private PatientSignUp1ViewModel viewModel;
    private EditText etUsername, etDate, etWeight, etHeight;
    private String selectedGender = "";
    private final Calendar calendar = Calendar.getInstance();
    private String token = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_screen_sign_up_1);
        fullscreen();

        // Bind
        etUsername = findViewById(R.id.Sp1_username_P);
        etDate = findViewById(R.id.Sp1_date_P);
        etWeight = findViewById(R.id.Sp1_weight_P);
        etHeight = findViewById(R.id.Sp1_height_P);
        RadioGroup rgGender = findViewById(R.id.Sp1_Gender_P);
        Button btnNext = findViewById(R.id.Sp1_bt_next_P);

        viewModel = new ViewModelProvider(this).get(PatientSignUp1ViewModel.class);
        observeViewModel();

        etDate.setOnClickListener(v -> openDatePicker());
        rgGender.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radio = findViewById(checkedId);

            selectedGender = radio.getText().toString();
        });

        btnNext.setOnClickListener(v -> handleNextClick());
        viewModel.fetchPatientToken();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void observeViewModel() {
        viewModel.getPatientToken().observe(this, tokenValue -> token = tokenValue);
        viewModel.getIsUsernameTaken().observe(this, taken -> {
            if (taken) {
                Toast.makeText(this, "اسم المستخدم مسجل مسبقًا", Toast.LENGTH_SHORT).show();
            } else {
              submitData();
            }
        });
    }
    private void handleNextClick() {
        String username = etUsername.getText().toString();
        String date = etDate.getText().toString();
        String weight = etWeight.getText().toString();
        String height = etHeight.getText().toString();

        if (username.isEmpty() || date.isEmpty() || weight.isEmpty() || height.isEmpty() || selectedGender.isEmpty()) {
            Toast.makeText(this, "يرجى تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.checkUsername(username);
    }
    private void submitData() {
        String username = etUsername.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String weight = etWeight.getText().toString().trim();
        String height = etHeight.getText().toString().trim();

        // تحقق من البيانات
        if (username.isEmpty() || date.isEmpty() || weight.isEmpty() || height.isEmpty() || selectedGender == null) {
            Toast.makeText(this, "الرجاء ملأ جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        String pin = String.valueOf(new Random().nextInt(9000) + 1000); // PIN عشوائي

        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("personal_info/date", date);
        data.put("personal_info/gender", selectedGender);
        data.put("personal_info/age", calculateAge(date));
        data.put("personal_info/weight", weight);
        data.put("personal_info/height", height);
        data.put("personal_info/PIN", pin);
        data.put("Token/Patient_Token", token);

        viewModel.saveStep1Data(username, data);
        Toast.makeText(this, "تم الحفظ بنجاح", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, PatientSignUpStep2Activity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
    private void openDatePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            etDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private String calculateAge(String birthDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Calendar dob = Calendar.getInstance();
            dob.setTime(Objects.requireNonNull(sdf.parse(birthDate)));
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return String.valueOf(age);
        } catch (Exception e) {
            return "0";
        }
    }

//    @SuppressLint("UseCompatLoadingForDrawables")
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        viewModel.exitHappen(this, databaseReference, DoctorUsername);
//    }
}
