package com.example.diabestes_care_app.Ui.Patient_all;

import static com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Home_Fragment.MyPREFERENCES_Patient_Profile;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_Profile_P extends Basic_Activity {
    Button request;
    ImageView back;
    CircleImageView Doctor_Profile;
    TextView DoctorName;
    // Dialog
    Dialog dialog;
    EditText et_title, et_subject;
    String Consultation_title, Consultation_subject, getName, getProfilePic, getUsername, getToken, PatientUsername, getPatientPic;
    DatabaseReference myReference;
    String chatKey;
    Notification_Number notification_number;
    SharedPreferences sharedpreferences, sharedpreferences2;
    public static final String MyPREFERENCES_MSGKey = "MSG_KEY";
    public static final String MyPREFERENCES_PushKey = "Push_Key";
    ArrayList<String> arrPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_doctor_profile_p);
        arrPackage = new ArrayList<>();

        //============================Define========================================================
        DoctorName = findViewById(R.id.DPP_tv_doctor_name);
        Doctor_Profile = findViewById(R.id.DPP_img_Profile);
        request = findViewById(R.id.request_for);
        back = findViewById(R.id.DPP_btn_back);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = Doctor_Profile_P.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        sharedpreferences = this.getSharedPreferences(MyPREFERENCES_MSGKey, MODE_PRIVATE);
        sharedpreferences2 = this.getSharedPreferences(MyPREFERENCES_PushKey, MODE_PRIVATE);

        SharedPreferences prefs2 = Doctor_Profile_P.this.getSharedPreferences(MyPREFERENCES_Patient_Profile, MODE_PRIVATE);
        getPatientPic = prefs2.getString("TAG_Image_P", null);

        //============================Get data from message adapter class===========================
        getName = getIntent().getStringExtra("Doctor_name");
        getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");
        getUsername = getIntent().getStringExtra("Doctor_username");
        getToken = getIntent().getStringExtra("Doctor_token");

        //============================Define Database Ref===========================================
        myReference = FirebaseDatabase.getInstance().getReference("doctor").child(getUsername).child("Consultation request").child("MSG").push();
        FirebaseMessaging.getInstance().subscribeToTopic(getUsername);
        String removeQuery = myReference.getKey();
        Log.e("TAG", removeQuery);

        arrPackage.add(removeQuery);
        Gson gson = new Gson();
        String json = gson.toJson(arrPackage);
        SharedPreferences.Editor editor = sharedpreferences2.edit();
        editor.putString("TAG_Push_Key", json);
        editor.commit();

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
        dialog = new Dialog(Doctor_Profile_P.this);
        dialog.setContentView(R.layout.consu_request_dialog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //============================Define Data Entry for request=================================
        Button oky = dialog.findViewById(R.id.okey);
        et_title = dialog.findViewById(R.id.et_titlee);
        et_subject = dialog.findViewById(R.id.et_subjectt);
        chatKey = "";

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(); // Showing the dialog here
            }
        });
        //============================load data from message adapter class==========================
        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consultation_title = et_title.getText().toString();
                Consultation_subject = et_subject.getText().toString();

                if (!Consultation_title.isEmpty() && !Consultation_subject.isEmpty() && !getToken.isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(getToken, Consultation_title, PatientUsername,getApplicationContext());
                    notificationsSender.SendNotifications();

                    myReference.child("Title").setValue(Consultation_title);
                    myReference.child("Subject").setValue(Consultation_subject);
                    myReference.child("from").setValue(PatientUsername);
                    myReference.child("to").setValue(getUsername);
                    myReference.child("Doctor_Profile").setValue(getProfilePic);
                    myReference.child("Patient_Profile").setValue(getPatientPic);
                    myReference.child("PushKey").setValue(removeQuery);
                    myReference.child("Doctor_Answer").setValue("null");

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