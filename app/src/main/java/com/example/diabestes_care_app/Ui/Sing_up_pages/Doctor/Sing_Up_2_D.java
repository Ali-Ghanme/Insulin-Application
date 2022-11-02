package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_2_D extends Basic_Activity {
    Button btn_next_S;
    EditText phone, email, NID, pass, co_Pass;
    DatabaseReference databaseReference;
    String doctor_userName;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_2_d);

        btn_next_S = findViewById(R.id.Sp2_bt_next_D);
        phone = findViewById(R.id.Sp2_et_phone_D);
        email = findViewById(R.id.Sp2_et_email_D);
        pass = findViewById(R.id.Sp2_et_Pass_D);
        co_Pass = findViewById(R.id.Sp2_et_coPass_D);
        NID = findViewById(R.id.Sp2_NID_D);

        Intent intentUsername = getIntent();
        doctor_userName = intentUsername.getStringExtra("username");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor");

        //====================================Set Data to Firebase==================================
        saveData();
        //============================Show password and hid password================================
        pass.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= pass.getRight() - pass.getCompoundDrawables()[Right].getBounds().width()) {
                    showPass(pass);
                    return true;
                }
            }
            return false;
        });

        //=================Show password and hid password edit text tow confirmation================
        co_Pass.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= co_Pass.getRight() - co_Pass.getCompoundDrawables()[Right].getBounds().width()) {
                    showPass(co_Pass);
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        exitHappen(this, databaseReference, doctor_userName);
    }

    void saveData() {
        btn_next_S.setOnClickListener(view -> {
            // get data form edit text into string variables
            String doctorPhone = phone.getText().toString();
            String doctorEmail = email.getText().toString();
            String doctorNID = NID.getText().toString();
            String doctorPass = pass.getText().toString();
            String doctorCoPass = co_Pass.getText().toString();

            //====================================Validation===============================
            // cheek if user fill all data fields before sending data to firebase
            if (validIsEmpty(doctorPhone, doctorEmail, doctorPass, doctorCoPass, doctorNID, doctorNID)) {
                Toast.makeText(Sing_Up_2_D.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else if (validCoPassword(doctorPass, doctorCoPass)) {
                Toast.makeText(Sing_Up_2_D.this, "password doesn't match", Toast.LENGTH_SHORT).show();
            } else if (isValidEmail(doctorEmail)) {
                Toast.makeText(Sing_Up_2_D.this, "Email is not correct Syntax", Toast.LENGTH_SHORT).show();
            } else if (validPassword(doctorPass)) {
                Toast.makeText(Sing_Up_2_D.this, "Password Must be more than 8 character", Toast.LENGTH_SHORT).show();
            } else if (validCellPhone(doctorPhone)) {
                Toast.makeText(Sing_Up_2_D.this, "Phone is not correct", Toast.LENGTH_SHORT).show();
            } else {
                // sending data to firebase real time
                // we are using a phone number as unique identity of every user
                databaseReference.child(doctor_userName).child("personal_info").child("Phone").setValue(doctorPhone);
                databaseReference.child(doctor_userName).child("personal_info").child("Email").setValue(doctorEmail);
                databaseReference.child(doctor_userName).child("personal_info").child("National Id").setValue(doctorNID);
                databaseReference.child(doctor_userName).child("Password").setValue(doctorPass);
                Toast.makeText(Sing_Up_2_D.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
                finish();

                Intent intent3 = new Intent(Sing_Up_2_D.this, Sing_Up_3_D.class);
                intent3.putExtra("username2", doctor_userName);
                startActivity(intent3);
            }
        });
    }
}