package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Sing_Up_2_P extends Basic_Activity {

    DatabaseReference databaseReference;
    Button btn_next;
    EditText phoneNumber, email, password, confirm_Password, city;
    ListView listView;
    String[] items = {"غزة", "رفح", "خانيونس", "ديرالبلح", "الوسطى", "الشمال"};
    String patient_userName;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_2_p);

        //============================Defines==================================
        city = findViewById(R.id.Sp2_et_city_P);
        btn_next = findViewById(R.id.Sp2_bt_next_P);
        phoneNumber = findViewById(R.id.Sp2_et_phone_P);
        email = findViewById(R.id.Sp2_et_email_P);
        password = findViewById(R.id.Sp2_et_Pass_P);
        confirm_Password = findViewById(R.id.Sp2_et_coPass_P);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");
        Intent intentUsername = getIntent();
        patient_userName = intentUsername.getStringExtra("username");

        //============================Spinner Function==============================================
        city.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_2_P.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_2_P.this)
                    .inflate(
                            R.layout.layout_bottom_sheet_main, findViewById(R.id.bottomSheetContier)
                    );
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_2_P.this, R.layout.activity_listview, items);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String cityS = listView.getAdapter().getItem(position).toString();
                city.setText(cityS);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //============================Show password and hid password================================
        password.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                    showPass(password);
                    return true;
                }
            }
            return false;
        });

        //=================Show password and hid password edit text tow confirmation================
        confirm_Password.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= confirm_Password.getRight() - confirm_Password.getCompoundDrawables()[Right].getBounds().width()) {
                    showPass(confirm_Password);
                    return true;
                }
            }
            return false;
        });

        //===================================================
        btn_next.setOnClickListener(view -> {

            // get data form edit text into string variables
            String patientPhone = phoneNumber.getText().toString();
            String patientEmail = email.getText().toString();
            String patientPass = password.getText().toString();
            String patientCoPass = confirm_Password.getText().toString();
            String patientCity = city.getText().toString();


            //====================================Validation===============================
            // cheek if user fill all data fields before sending data to firebase
            if (validIsEmpty(patientPhone, patientEmail, patientPass, patientCoPass, patientCity, patientCity)) {
                Toast.makeText(Sing_Up_2_P.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            } else if (validCoPassword(patientPass, patientCoPass)) {
                Toast.makeText(Sing_Up_2_P.this, "password doesn't match", Toast.LENGTH_SHORT).show();
            } else if (isValidEmail(patientEmail)) {
                Toast.makeText(Sing_Up_2_P.this, "Email is not correct Syntax", Toast.LENGTH_SHORT).show();
            } else if (validPassword(patientPass)) {
                Toast.makeText(Sing_Up_2_P.this, "Password Must be more than 8 character", Toast.LENGTH_SHORT).show();
            } else if (validCellPhone(patientPhone)) {
                Toast.makeText(Sing_Up_2_P.this, "Phone is not correct", Toast.LENGTH_SHORT).show();
            } else {
                // sending data to firebase real time
                // we are using a phone number as unique identity of every user
                databaseReference.child(patient_userName).child("personal_info").child("Phone").setValue(patientPhone);
                databaseReference.child(patient_userName).child("personal_info").child("Email").setValue(patientEmail);
                databaseReference.child(patient_userName).child("Password").setValue(patientPass);
                databaseReference.child(patient_userName).child("CoPassword").setValue(patientCoPass);
                databaseReference.child(patient_userName).child("personal_info").child("City").setValue(patientCity);
                Toast.makeText(Sing_Up_2_P.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
                finish();

                Intent intent3 = new Intent(Sing_Up_2_P.this, Sing_Up_3_P.class);
                intent3.putExtra("username2", patient_userName);
                startActivity(intent3);
            }
        });
    }

    public void onBackPressed() {
        exitHappen(this, databaseReference, patient_userName);
    }


}