package com.example.diabestes_care_app.Ui.Patient_all;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.NotificationSender.FcmNotificationsSender;
import com.example.diabestes_care_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class Doctor_Profile_P extends Basic_Activity {
    Button request;
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

        // FirebaseMessaging.getInstance().subscribeToTopic("all");
        // Get data from message adapter class
        final String getName = getIntent().getStringExtra("Doctor name");
        final String getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");
        final String getUsername = getIntent().getStringExtra("username");
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

        Button oky = dialog.findViewById(R.id.okey);
        EditText et_titlee = dialog.findViewById(R.id.et_titlee);
        EditText et_subjectt = dialog.findViewById(R.id.et_subjectt);

        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_titlee.getText().toString().isEmpty() && !et_subjectt.getText().toString().isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/" + getUsername, et_titlee.getText().toString(),
                            et_subjectt.getText().toString(), getApplicationContext(), Doctor_Profile_P.this);
                    notificationsSender.SendNotifications();



                    //##### Send Notification All  User
              /*
      if (!et_titlee.getText().toString().isEmpty() && !et_subjectt.getText().toString().isEmpty()) {
     FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all", et_titlee.getText().toString(),
         et_subjectt.getText().toString(), getApplicationContext(), Doctor_Profile_P.this);
      notificationsSender.SendNotifications();
         Toast.makeText(getApplicationContext(), "Yes , text && Title required", Toast.LENGTH_LONG).show();
               */
                    dialog.dismiss();  // اخفاء الدايلوج
                } else {
                    //   Toast.makeText(getApplicationContext(), "No , text && Title required", Toast.LENGTH_LONG).show();
                }

            }
        });


        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(); // Showing the dialog here
            }
        });

    }
}