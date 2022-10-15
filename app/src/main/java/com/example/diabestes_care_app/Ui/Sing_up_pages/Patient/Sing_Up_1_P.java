package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;


public class Sing_Up_1_P extends Basic_Activity {

    DatabaseReference databaseReference;
    Button btn_next_S;
    EditText mName, mUsername, mDate, mWehigt, mTall;
    final Calendar myCalendar = Calendar.getInstance();
    RadioGroup mGender;
    RadioButton mGenderOption;
    String strGender;
    String patientName, patientUsername, patientDate, PatientAge, PatientToken;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_1_p);

        //====================================Define===============================
        btn_next_S = findViewById(R.id.Sp1_bt_next_P);
        mName = findViewById(R.id.Sp1_name_P);
        mUsername = findViewById(R.id.Sp1_username_P);
        mGender = findViewById(R.id.Sp1_Gender_P);
        mDate = findViewById(R.id.Sp1_date_P);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");
        // Generate Token for Patient
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            // Get new FCM registration token
            PatientToken = task.getResult();
            System.out.println("TOKEN" + PatientToken);
        });
        //====================================DataPicker===============================
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel(mDate, myCalendar, "dd/MM/yyyy");
            PatientAge = getAge(year, month, day);
        };
        mDate.setOnClickListener(view -> new DatePickerDialog(Sing_Up_1_P.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        //====================================Gender Radio Group to get Data And set into Firebase===============================
        mGender.setOnCheckedChangeListener((group, checkedId) -> {
            mGenderOption = mGender.findViewById(checkedId);
            switch (checkedId) {
                case R.id.Sp1_male_P:
                case R.id.Sp1_female_P:
                    strGender = mGenderOption.getText().toString();
                    break;
                default:
            }
        });

        //====================================Gender Radio Group to get Data And set into Firebase===============================
        btn_next_S.setOnClickListener(view -> {
            // get data form edit text into string variables
            patientName = mName.getText().toString();
            patientUsername = mUsername.getText().toString();
            patientDate = mDate.getText().toString();
//            patientWehigt = mWehigt.getText().toString();
//            patientTall = mTall.getText().toString();

            //====================================Validation===============================
            // cheek if user fill all data fields before sending data to firebase
            if (validIsEmpty(patientName, patientUsername, patientDate, patientDate, patientDate, patientDate)) {
                Toast.makeText(Sing_Up_1_P.this, "الرجاء ملأ جميع الحقول", Toast.LENGTH_SHORT).show();
            } else if (patientUsername.startsWith("P", 1)) {
                Toast.makeText(Sing_Up_1_P.this, "أبدأ اسم المستخدم ب حرف P", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // check if username is not registered before
                        if (snapshot.hasChild(patientUsername)) {
                            Toast.makeText(Sing_Up_1_P.this, "ID is already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            // sending data to firebase real time
                            // we are using a phone number as unique identity of every user
                            databaseReference.child(patientUsername).child("personal_info").child("name").setValue(patientName);
                            databaseReference.child(patientUsername).child("username").setValue(patientUsername);
                            databaseReference.child(patientUsername).child("personal_info").child("date").setValue(patientDate);
                            databaseReference.child(patientUsername).child("personal_info").child("gender").setValue(strGender);
                            databaseReference.child(patientUsername).child("personal_info").child("Age").setValue(PatientAge);
                            databaseReference.child(patientUsername).child("Token").child("Patient_Token").setValue(PatientToken);
                            Toast.makeText(Sing_Up_1_P.this, PatientAge, Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(Sing_Up_1_P.this, Sing_Up_2_P.class);
                            intent2.putExtra("username", patientUsername);
                            startActivity(intent2);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("My_Error", error.getMessage());
                    }
                });
            }
        });
    }

    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        int ageInt = age;
        String ageS = Integer.toString(ageInt);
        Log.e("TAG", ageS);
        return ageS;
    }

    public void onBackPressed() {
        exitHappen(this, databaseReference, patientUsername);
    }

}
