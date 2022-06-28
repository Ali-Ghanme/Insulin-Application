package com.example.diabestes_care_app.Ui.Patient_all;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_Profile_P extends Basic_Activity {
    Button request;
    ImageView back;
    CircleImageView Doctor_Profile;
    TextView DoctorName;
    // Dialog
    Dialog dialog;
    EditText et_title, et_subject;
    String Consultation_title, Consultation_subject, getName, getProfilePic, getUsername;
    DatabaseReference myReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_doctor_profile_p);

        //============================Define========================================================
        DoctorName = findViewById(R.id.DPP_tv_doctor_name);
        Doctor_Profile = findViewById(R.id.DPP_img_Profile);
        request = findViewById(R.id.request_for);
        back = findViewById(R.id.DPP_btn_back);

        //============================Define Database Ref===========================================
        myReference = FirebaseDatabase.getInstance().getReference("Consultation request");

        //============================Back Button Action============================================
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_Profile_P.this, Home_Patient.class);
                startActivity(intent);
            }
        });

        //============================Get data from message adapter class===========================
          getName = getIntent().getStringExtra("Doctor name");
          getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");
          getUsername = getIntent().getStringExtra("username");
        //============================load data from message adapter class==========================
        DoctorName.setText(getName);
        Glide.with(this).load(getProfilePic).into(Doctor_Profile);
//        FirebaseMessaging.getInstance().subscribeToTopic("DMohammed");

        //============================Create + Configure the Dialog here============================
        FirebaseMessaging.getInstance().subscribeToTopic(getUsername);
        dialog = new Dialog(Doctor_Profile_P.this);
        dialog.setContentView(R.layout.genral_message_layout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //============================Define Data Entry for request=================================
        Button oky = dialog.findViewById(R.id.okey);
        et_title = dialog.findViewById(R.id.et_titlee);
        et_subject = dialog.findViewById(R.id.et_subjectt);


        //============================load data from message adapter class==========================
        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Consultation_title = et_title.getText().toString();
                Consultation_subject = et_subject.getText().toString();

                myReference.child("MSG").child("Title").setValue(Consultation_title);
                myReference.child("MSG").child("Subject").setValue(Consultation_subject);
                notification();
                dialog.dismiss();
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(); // Showing the dialog here
            }
        });
    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentText("Code")
                .setContentTitle("إستشارة من المريض")
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentText("New Data On Firebase");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(999, builder.build());
    }
}