package com.example.diabestes_care_app.Ui.Patient_all;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_doctor_profile_p);

        DoctorName = findViewById(R.id.HP_doctor_name);
        Doctor_Profile = findViewById(R.id.HP_profile_img);

        // Get data from message adapter class
        final String getName = getIntent().getStringExtra("Doctor name");
        final String getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");

        DoctorName.setText(getName);
        Glide.with(this).load(getProfilePic).into(Doctor_Profile);
    }
}
