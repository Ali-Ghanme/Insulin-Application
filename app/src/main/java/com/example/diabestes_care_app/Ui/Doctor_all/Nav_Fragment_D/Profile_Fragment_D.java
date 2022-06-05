package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

import static android.content.Context.MODE_PRIVATE;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_P;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Help;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile_Fragment_D extends Fragment {

    // Section
    RelativeLayout notification_cont, DarkMode_cont, help_cont, LogOut_cont;
    // Doctor Profile Image
    ImageView imageView;
    // Doctor UserName + name
    TextView name;
    //Firebase
    DatabaseReference myRef;
    //Username for SharedPreference
    String restoredText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile___d, container, false);

        //============================Defines=======================================================
        notification_cont = view.findViewById(R.id.FB_notification_cont_d);
        DarkMode_cont = view.findViewById(R.id.FB_DarkMode_cont_d);
        help_cont = view.findViewById(R.id.FB_help_cont_d);
        LogOut_cont = view.findViewById(R.id.FB_LogOut_cont_d);
        name = view.findViewById(R.id.FB_tv_doctor_name_d);
        imageView = view.findViewById(R.id.FB_Doctor_image_d);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //============================Next Click Listener===========================================
        notification_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), notification.class);
                startActivity(intent);
            }
        });
        DarkMode_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hallow Dark Mod is Unavailable right now 😉", Toast.LENGTH_SHORT).show();
            }
        });
        help_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Help.class);
                startActivity(intent);
            }
        });
        LogOut_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
              getActivity().finish();

                Toast.makeText(getContext(), "Hallow LogOut is Unavailable right now 😉", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //============================Get And Show Patient Data=========================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String PatientImage = snapshot.child(restoredText).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                String PatientName = snapshot.child(restoredText).child("personal_info").child("name_ar").getValue(String.class);
                Log.d("TAG", name + "/" + PatientImage);
                Glide.with(getActivity()).load(PatientImage).into(imageView);
                name.setText(PatientName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    // مشان يا غزة غزة يلا
}