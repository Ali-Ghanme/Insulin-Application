package com.example.diabestes_care_app.Ui.Patient_all;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.NotificationSender.FcmNotificationsSender;
import com.example.diabestes_care_app.Notification_Controller.Notification_Number;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Consulation.Consultation_Request;
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
    String Consultation_title, Consultation_subject, getName, getProfilePic, getUsername, getToken, PatientUsername,getPatientPic;
    DatabaseReference myReference,myRef;
    String chatKey;
    Notification_Number notification_number;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES_MSGKey = "MSG_KEY";

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

        //============================Get data from message adapter class===========================
        getName = getIntent().getStringExtra("Doctor_name");
        getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");
        getUsername = getIntent().getStringExtra("Doctor_username");
        getToken = getIntent().getStringExtra("Doctor_token");

        sharedpreferences = this.getSharedPreferences(MyPREFERENCES_MSGKey, MODE_PRIVATE);

        SharedPreferences prefs = Doctor_Profile_P.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //============================Define Database Ref===========================================
        myReference = FirebaseDatabase.getInstance().getReference("doctor").child(getUsername).child("Consultation request").child("MSG").push();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        //============================Back Button Action============================================
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_Profile_P.this, Home_Patient.class);
                startActivity(intent);
            }
        });

        //============================load data from message adapter class==========================
        DoctorName.setText(getName);
        Glide.with(this).load(getProfilePic).into(Doctor_Profile);

        //============================Create + Configure the Dialog here============================
        FirebaseMessaging.getInstance().subscribeToTopic(getUsername);
        dialog = new Dialog(Doctor_Profile_P.this);
        dialog.setContentView(R.layout.genral_message_layout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Intent intent = new Intent(this, Consultation_Request.class);
        //============================Define Data Entry for request=================================
        Button oky = dialog.findViewById(R.id.okey);
        et_title = dialog.findViewById(R.id.et_titlee);
        et_subject = dialog.findViewById(R.id.et_subjectt);
        chatKey = "";

        //============================load data from message adapter class==========================
        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consultation_title = et_title.getText().toString();
                Consultation_subject = et_subject.getText().toString();

                if (!Consultation_title.isEmpty() && !Consultation_subject.isEmpty() && !getToken.isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(getToken, Consultation_title, PatientUsername, getApplicationContext(),
                            Doctor_Profile_P.this);
                    notificationsSender.SendNotifications();

                    myReference.child("from").setValue(PatientUsername);
                    myReference.child("Title").setValue(Consultation_title);
                    myReference.child("Subject").setValue(Consultation_subject);
                    myReference.child("to").setValue(getUsername);
                    myReference.child("Doctor_Profile").setValue(getProfilePic);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("TAG_NAME2", chatKey);
                    editor.commit();
                    Log.e("TAG", chatKey);

                } else {
                    Toast.makeText(Doctor_Profile_P.this, "Enter Token", Toast.LENGTH_SHORT).show();
                }
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