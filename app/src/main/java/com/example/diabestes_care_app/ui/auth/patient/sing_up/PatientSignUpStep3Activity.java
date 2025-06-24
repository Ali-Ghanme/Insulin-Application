package com.example.diabestes_care_app.ui.auth.patient.sing_up;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.diabestes_care_app.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.viewmodel.auth.sing_up.PatientSignUp3ViewModel;
import com.google.firebase.database.FirebaseDatabase;

public class PatientSignUpStep3Activity extends Basic_Activity {
    private PatientSignUp3ViewModel viewModel;
    private String patient_userName;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_screen_sign_up_3);

        viewModel = new ViewModelProvider(this).get(PatientSignUp3ViewModel.class);

        // استلام اسم المستخدم
        Intent intentUsername = getIntent();
        patient_userName = intentUsername.getStringExtra("username2");

        // الربط مع الواجهة
        RadioGroup RG_mType = findViewById(R.id.Sp3_RG_type_P);
        Button btn_next = findViewById(R.id.Sp3_bt_next_P);
        Button btn_next2 = findViewById(R.id.Sp3_bt_next_P2);

        CheckBox[] medicsCheckboxes = {
                findViewById(R.id.Sp3_medics_types_1_P),
                findViewById(R.id.Sp3_medics_types_2_P),
                findViewById(R.id.Sp3_medics_types_3_P),
                findViewById(R.id.Sp3_medics_types_4_P),
                findViewById(R.id.Sp3_medics_types_5_P),
                findViewById(R.id.Sp3_medics_types_6_P),
                findViewById(R.id.Sp3_medics_types_7_P)
        };

        for (CheckBox checkBox : medicsCheckboxes) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) viewModel.addMedicType(buttonView.getText().toString());
            });
        }

        RG_mType.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selected = group.findViewById(checkedId);
            if (selected != null) {
                viewModel.setDiabetesType(selected.getText().toString());
            }
        });

        View.OnClickListener onNext = v -> {
            if (!viewModel.isValid()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.saveDiseaseInfo(patient_userName, task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(PatientSignUpStep3Activity.this, PatientSignUpStep4Activity.class);
                    intent.putExtra("username3", patient_userName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
                }
            });
        };

        btn_next.setOnClickListener(onNext);
        btn_next2.setOnClickListener(onNext);
    }

    @Override
    public void onBackPressed() {
        exitHappen(this, FirebaseDatabase.getInstance().getReference().child("patient"), patient_userName);
    }
}
