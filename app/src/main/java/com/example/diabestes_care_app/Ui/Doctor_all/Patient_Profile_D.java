package com.example.diabestes_care_app.Ui.Doctor_all;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Patient_Profile_D extends Basic_Activity {
    ImageView patient_profile, back;
    TextView name, type, age, gender;
    TextView medicsType, another, injury_date, injury_factor;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_d);

        // Get data from message adapter class
        final String getUsername = getIntent().getStringExtra("username");

        //=========================================Define===========================================
        patient_profile = findViewById(R.id.FPI_img_Profile_d);
        name = findViewById(R.id.FPI_name_d);
        type = findViewById(R.id.FPI_type_d);
        age = findViewById(R.id.FPI_age_d);
        gender = findViewById(R.id.FPI_gender_d);
        medicsType = findViewById(R.id.FPI_Medics_Type_d);
        another = findViewById(R.id.FPI_another_d);
        injury_date = findViewById(R.id.FPI_injury_date_d);
        injury_factor = findViewById(R.id.FPI_injury_factor_d);
        back = findViewById(R.id.DPP_btn_back_d);

        back.setOnClickListener(v -> onBackPressed());
        // Get data from message adapter class
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(getUsername);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (this == null) {
                    return;
                }
                String image = Objects.requireNonNull(snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue()).toString();
                Glide.with(getApplicationContext()).load(image).into(patient_profile);
                name.setText(snapshot.child("personal_info").child("name").getValue().toString());
                age.setText(snapshot.child("personal_info").child("Age").getValue().toString());
                gender.setText(snapshot.child("personal_info").child("gender").getValue().toString());
                type.setText(snapshot.child("disease_info").child("Diabetes Type").getValue().toString());
                medicsType.setText(snapshot.child("disease_info").child("Diabetes Medics Type").getValue().toString());
                another.setText(snapshot.child("disease_info").child("أمراض أخرى").getValue().toString());
                injury_date.setText(snapshot.child("disease_info").child("تاريخ الاصابة").getValue().toString());
                injury_factor.setText(snapshot.child("disease_info").child("عوامل الاصابة").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}