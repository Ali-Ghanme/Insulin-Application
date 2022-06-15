package com.example.diabestes_care_app.Ui.Patient_all;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_Profile_P extends Basic_Activity {
    Button request;
    DatabaseReference myRef;
    CircleImageView Doctor_Profile;
    TextView DoctorName;
    // Dialog
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_doctor_profile_p);

        DoctorName = findViewById(R.id.HP_doctor_name);
        Doctor_Profile = findViewById(R.id.HP_profile_img);
        request = findViewById(R.id.request_for);

        // Get data from message adapter class
        final String getName = getIntent().getStringExtra("Doctor name");
        final String getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");

        DoctorName.setText(getName);
        Glide.with(this).load(getProfilePic).into(Doctor_Profile);

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(Doctor_Profile_P.this);
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

//        Okay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getContext(), "Okay", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(); // Showing the dialog here
            }
        });
    }
}
