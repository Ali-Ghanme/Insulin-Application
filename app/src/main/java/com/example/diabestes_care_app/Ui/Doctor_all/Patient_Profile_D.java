package com.example.diabestes_care_app.Ui.Doctor_all;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;

import de.hdodenhof.circleimageview.CircleImageView;

public class Patient_Profile_D extends Basic_Activity {
    Button request;
    ImageView back;
    CircleImageView Doctor_Profile;
    TextView DoctorName;
    // Dialog
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_d);

        DoctorName = findViewById(R.id.PPD_tv_doctor_name);
        Doctor_Profile = findViewById(R.id.PPD_img_Profile);
        request = findViewById(R.id.PPD_request_for);
        back = findViewById(R.id.PPD_btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_Profile_D.this, Home_Patient.class);
                startActivity(intent);
            }
        });

        // Get data from message adapter class
        final String getName = getIntent().getStringExtra("Patient name");
        final String getProfilePic = getIntent().getStringExtra("Patient_Pic_Profile");
        final String getUsername = getIntent().getStringExtra("username");
        final String getType = getIntent().getStringExtra("type");

        DoctorName.setText(getName);
        Glide.with(this).load(getProfilePic).into(Doctor_Profile);
        Toast.makeText(this, getUsername, Toast.LENGTH_SHORT).show();

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(Patient_Profile_D.this);
        dialog.setContentView(R.layout.consu_request_dialog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button oky = dialog.findViewById(R.id.okey);
        EditText et_title = dialog.findViewById(R.id.et_titlee);
        EditText et_subject = dialog.findViewById(R.id.et_subjectt);
    }
}