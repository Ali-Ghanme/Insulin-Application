package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Sing_Up_4_P extends Basic_Activity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    Button btn_next;
    EditText mDateInjury, mCause, mDevice, others;
    final Calendar myCalendar = Calendar.getInstance();
    CheckBox C_Other;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_p);

        //====================================Define Checkbox===============================
        btn_next = findViewById(R.id.Sp4_bt_next_P);
        mDateInjury = findViewById(R.id.Sp4_et_enjerdDate_P);
        mCause = findViewById(R.id.Sp4_et_cause_P);
        mDevice = findViewById(R.id.Sp4_et_have_device_P);
        others = findViewById(R.id.SP4_et_others_P);
        C_Other = findViewById(R.id.Sp4_diabetis_others_P);

        C_Other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                others.setFocusable(isChecked);
            }
        });

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username3");
        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sing_Up_4_P.this, "Hallow", Toast.LENGTH_SHORT).show();

                // get data form edit text into string variables
                String patientDateInjury = mDateInjury.getText().toString();
                String patientCauses = mCause.getText().toString();
                String patientDevice = mDevice.getText().toString();
                String patientOthers = others.getText().toString();
                String patientCheck = C_Other.getText().toString();

                databaseReference.child("patient").child(patient_userName).child("disease_info").child("Patient Date Injury ").setValue(patientDateInjury);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("Patient Causes").setValue(patientCauses);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("Does the patient have a blood glucose meter?").setValue(patientDevice);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("Does the patient have any other chronic diseases?").setValue(patientOthers);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("Have issue").setValue(patientCheck);
                Toast.makeText(Sing_Up_4_P.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
                finish();

                Intent intent4 = new Intent(Sing_Up_4_P.this, Sing_Up_5_P.class);
                intent4.putExtra("username4", patient_userName);
                startActivity(intent4);
            }
        });


        //====================================DataPicker===============================
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        mDateInjury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Sing_Up_4_P.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        mDateInjury.setText(dateFormat.format(myCalendar.getTime()));
    }
}