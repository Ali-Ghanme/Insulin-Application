package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;


public class Sing_Up_1_P extends Basic_Activity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    Button btn_next_S;
    EditText mName, mUsername, mDate, mWehigt, mTall, mID;
    final Calendar myCalendar = Calendar.getInstance();
    RadioGroup mGender;
    RadioButton mGenderOption;
    String strGender;
    String patientName, patientUsername, patientDate, patientWehigt, patientTall, PatientID, PatientAge, PatientToken;


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
        mWehigt = findViewById(R.id.Sp1_wehigt_P);
        mTall = findViewById(R.id.Sp1_tall_P);
        mID = findViewById(R.id.Sp1_ID_P);


        // Generate Token for Patient
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                // Get new FCM registration token
                PatientToken = task.getResult();
                System.out.println("TOKEN" + PatientToken);
            }
        });
        //====================================DataPicker===============================
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel(mDate, myCalendar, "dd/MM/yyyy");
                PatientAge = getAge(year, month, day);
            }
        };
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Sing_Up_1_P.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //====================================Gender Radio Group to get Data And set into Firebase===============================
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mGenderOption = mGender.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.Sp1_male_P:
                        strGender = mGenderOption.getText().toString();
                        break;
                    case R.id.Sp1_female_P:
                        strGender = mGenderOption.getText().toString();
                        break;
                    default:
                }
            }
        });

        //====================================Gender Radio Group to get Data And set into Firebase===============================
        btn_next_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data form edit text into string variables
                patientName = mName.getText().toString();
                patientUsername = mUsername.getText().toString();
                patientDate = mDate.getText().toString();
                patientWehigt = mWehigt.getText().toString();
                patientTall = mTall.getText().toString();
                PatientID = mID.getText().toString();


                //====================================Validation===============================
                // cheek if user fill all data fields before sending data to firebase
                if (validIsEmpty(patientName, patientUsername, patientWehigt, patientTall, PatientID, patientDate)) {
                    Toast.makeText(Sing_Up_1_P.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!patientUsername.startsWith("P")) {
                    Toast.makeText(Sing_Up_1_P.this, "User Name Must Start With P", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("patient").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if username is not registered before
                            if (snapshot.hasChild(patientUsername)) {
                                Toast.makeText(Sing_Up_1_P.this, "ID is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                // sending data to firebase real time
                                // we are using a phone number as unique identity of every user
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("name").setValue(patientName);
                                databaseReference.child("patient").child(patientUsername).child("username").setValue(patientUsername);
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("date").setValue(patientDate);
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("gender").setValue(strGender);
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("wehigt").setValue(patientWehigt);
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("tall").setValue(patientTall);
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("iD").setValue(PatientID);
                                databaseReference.child("patient").child(patientUsername).child("personal_info").child("Age").setValue(PatientAge);
                                databaseReference.child("patient").child(patientUsername).child("Token").child("Patient_Token").setValue(PatientToken);
                                Toast.makeText(Sing_Up_1_P.this, PatientAge, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(Sing_Up_1_P.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
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
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        Log.e("TAG", ageS);
        return ageS;
    }
}
