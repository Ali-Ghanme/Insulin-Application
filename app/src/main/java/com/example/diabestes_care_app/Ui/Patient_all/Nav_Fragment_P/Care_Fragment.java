package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Care_Fragment extends Fragment {
    //ImageView
    ImageView info;
    ImageView imageProfile;
    //TextView
    TextView username;
    // Dialog
    Dialog dialog;
    // implements Interface_Recycle
    FirebaseDatabase db;
    // Firebase
    DatabaseReference myRef;
    // ShardPreference
    public static final String MyPREFERENCES_P = "P_Username";
    // Patient Username TextView
    String restoredText;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_care_, container, false);

        //============================Defines=======================================================
        info = view.findViewById(R.id.FC_info);
        imageProfile = view.findViewById(R.id.FC_profile_img);
        username = view.findViewById(R.id.FC_patient_name);

        //============================Get Doctor Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(getContext());
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

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(); // Showing the dialog here
            }
        });

        return view;
    }

    //============================Show The Patient name + image=====================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.child(restoredText).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                String name = snapshot.child(restoredText).child("personal_info").child("name").getValue(String.class);
                Glide.with(getActivity()).load(image).into(imageProfile);
                Log.d("TAG", name + "/" + image);
                username.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
}