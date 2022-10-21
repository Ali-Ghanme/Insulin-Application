package com.example.diabestes_care_app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.ui.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {
    Dialog dialog;
    Button request, oky, send_email;

    EditText et_email, et_username, et_subject;
    String pin, email ,emailto;


    // Firebase
    DatabaseReference databaseReference;
    String PatientUsername;
    public static final String MyPREFERENCES_P = "P_Username";

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        et_email = findViewById(R.id.et_email);
        et_username = findViewById(R.id.et_username);
        send_email = findViewById(R.id.send_email);


        //============================Firebase============================================
        SharedPreferences prefs = this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("personal_info");
        Query query = databaseReference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   if (dataSnapshot != null) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        pin = snapshot.child("PIN").getValue().toString();
                        email = snapshot.child("Email").getValue().toString();

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "حدث خطأ - لا تقلق ", Toast.LENGTH_SHORT).show();
                }


//               // Create + Configure the Dialog here
//               dialog = new Dialog(ResetPassword.this);
//               dialog.setContentView(R.layout.chech_email_and_phone);
//               dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
//               //Setting the animations to dialog
//               dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//               dialog.setCancelable(true); //Optional
//               dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//
//               Button oky = dialog.findViewById(R.id.okey);
//

                String to = et_email.getText().toString();

                // String message=editTextMessage.getText().toString();
                //        chatKey = "";
                send_email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (to.equals(email)) {
                            Log.e("Eq","send email equals");
                            sendEmail(to , pin);
//                    Intent email = new Intent(Intent.ACTION_SEND);
//                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{
//                            to
//                    });
//                    email.putExtra(Intent.EXTRA_SUBJECT, subject);
//                    email.putExtra(Intent.EXTRA_TEXT, pin);
//
////need this to prompts email client only
//                    email.setType("message/rfc822");
//
//                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
//

                        } else {
                            Toast.makeText(ResetPassword.this, "email not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        //============================Show Consu Dialog and send request for doctor=================
//        request.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show(); // Showing the dialog here
//            }
//        });
//
//
//        oky.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ResetPassword.this, "Success Process", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @SuppressLint("IntentReset")
    public void sendEmail(String emailto ,String  pin) {
        String[] TO = {"mohammedsiam159@gmail.com"};
        String[] CC = {emailto};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "رمز اعادة كلمة المرور ");
        emailIntent.putExtra(Intent.EXTRA_TEXT, pin);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
