package com.example.diabestes_care_app.Ui.Patient_all;

import static com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Home_Fragment.MyPREFERENCES_Patient_Profile;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.NotificationSender.FcmNotificationsSender;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_Profile_P extends Basic_Activity {

    // Set The Preference
    public static final String MyPREFERENCES_MSGKey = "MSG_KEY";
    public static final String MyPREFERENCES_PushKey = "Push_Key";
    // Set data on Shared Preference
    SharedPreferences sharedpreferences, SharedPreferences2;
    // Firebase Reference
    DatabaseReference myReference, PatientReferences;
    // Layout Item
    Button request;
    ImageView back;
    CircleImageView Doctor_Profile;
    TextView DoctorName;
    EditText et_title, et_subject;
    Dialog dialog;
    // String Var
    String Consultation_title, Consultation_subject, getName, getProfilePic, getUsername, getToken, PatientUsername, getPatientPic, chatKey, removeQuery, PatientToken;
    // Arraylist
    ArrayList<String> arrPackage;

    @SuppressLint("UseCompatLoadingForDrawables")
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

        // Define Shared preference
        sharedpreferences = this.getSharedPreferences(MyPREFERENCES_MSGKey, MODE_PRIVATE);
        SharedPreferences2 = this.getSharedPreferences(MyPREFERENCES_PushKey, MODE_PRIVATE);

        // Define Arraylist
        arrPackage = new ArrayList<>();

        // Create + Configure the Dialog here
        dialog = new Dialog(Doctor_Profile_P.this);
        dialog.setContentView(R.layout.consu_request_dialog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));

        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //============================Get data from=================================================
        // Get Patient Username
        SharedPreferences prefs = Doctor_Profile_P.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        // Get Patient Profile Image
        SharedPreferences prefs2 = Doctor_Profile_P.this.getSharedPreferences(MyPREFERENCES_Patient_Profile, MODE_PRIVATE);
        getPatientPic = prefs2.getString("TAG_Image_P", null);

        // Get Doctor Data From Intent from adapter
        getName = getIntent().getStringExtra("Doctor_name");
        getProfilePic = getIntent().getStringExtra("Doctor_Pic_Profile");
        getUsername = getIntent().getStringExtra("Doctor_username");
        getToken = getIntent().getStringExtra("Doctor_token");

        //============================Define Data Base =============================================
        // Define Database Ref
        PatientReferences = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername);
        getPatientToken();
        myReference = FirebaseDatabase.getInstance().getReference("doctor").child(getUsername).child("Consultation request").child("MSG").push();
        FirebaseMessaging.getInstance().subscribeToTopic(getUsername);

        // Store Data On Gson
        removeQuery = myReference.getKey();
        arrPackage.add(removeQuery);
        Gson gson = new Gson();
        String json = gson.toJson(arrPackage);
        SharedPreferences.Editor editor = SharedPreferences2.edit();
        editor.putString("TAG_Push_Key", json);
        editor.apply();

        //============================Back Button Action============================================
        back.setOnClickListener(v -> {
            onBackPressed();
        });

        //============================Load Data To Ui===============================================
        // Doctor Name
        DoctorName.setText(getName);

        // Doctor Image
        Glide.with(this).load(getProfilePic).into(Doctor_Profile);

        //============================Define Data Entry for request=================================
        Button oky = dialog.findViewById(R.id.okey);
        et_title = dialog.findViewById(R.id.et_titlee);
        et_subject = dialog.findViewById(R.id.et_subjectt);
        chatKey = "";

        //============================Show Consu Dialog and send request for doctor=================
        request.setOnClickListener(v -> {
            dialog.show(); // Showing the dialog here
        });
        oky.setOnClickListener(v -> setConsuData());
    }

    private void getPatientToken() {
        PatientReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    PatientToken = Objects.requireNonNull(snapshot.child("Token").child("Patient_Token").getValue()).toString();
                    Log.e("TAG", PatientToken);
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    private void setConsuData() {
        Consultation_title = et_title.getText().toString();
        Consultation_subject = et_subject.getText().toString();

        if (!Consultation_title.isEmpty() && !Consultation_subject.isEmpty() && !getToken.isEmpty()) {
            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(getToken, Consultation_title, PatientUsername, getApplicationContext());
            notificationsSender.SendNotifications();

            myReference.child("Title").setValue(Consultation_title);
            myReference.child("Subject").setValue(Consultation_subject);
            myReference.child("from").setValue(PatientUsername);
            myReference.child("to").setValue(getUsername);
            myReference.child("Doctor_Profile").setValue(getProfilePic);
            myReference.child("Patient_Profile").setValue(getPatientPic);
            myReference.child("PushKey").setValue(removeQuery);
            myReference.child("Doctor_Answer").setValue("null");
            myReference.child("PatientToken").setValue(PatientToken);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("TAG_NAME2", chatKey);
            editor.apply();

            Log.e("TAG", chatKey);

        } else {
            Toast.makeText(Doctor_Profile_P.this, "لا يمكن أن تكون الإستشارة فارغة", Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();
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