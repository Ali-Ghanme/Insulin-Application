package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Consulation.Consultation_Request;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Doses.ui.MainActivity;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Patient_Files;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Self_Care;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Care_Fragment extends Fragment {
    //ImageView
    ImageView info, imageProfile;
    //TextView
    TextView username;
    // Dialog
    Dialog dialog;
    // Firebase
    DatabaseReference myRef;
    // Patient Username TextView
    String PatientUsername;
    //Card View
    CardView FC_Instruction, FC_Report, FC_Doses_p, FC_Consu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_care_, container, false);
        //============================Defines=======================================================
        info = view.findViewById(R.id.FC_info);
        imageProfile = view.findViewById(R.id.FC_profile_img);
        username = view.findViewById(R.id.FC_patient_name);
        FC_Instruction = view.findViewById(R.id.FC_SelfCare_box_p);
        FC_Report = view.findViewById(R.id.FC_Reports_p);
        FC_Doses_p = view.findViewById(R.id.FC_Doses_p);
        FC_Consu = view.findViewById(R.id.FC_ex_Consultation_p);

        //============================Get Doctor Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //=================================Blocks===================================================
        FC_Instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Self_Care.class);
                startActivity(intent);
            }
        });
        FC_Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Patient_Files.class);
                startActivity(intent);
            }
        });
        FC_Doses_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        FC_Consu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Consultation_Request.class);
                startActivity(intent);
            }
        });

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button Okay = dialog.findViewById(R.id.btn_okay);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Okay", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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
                if (getActivity() == null) {
                    return;
                }
                String image = snapshot.child(PatientUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                String name = snapshot.child(PatientUsername).child("personal_info").child("name").getValue(String.class);
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
// Hallow this is Update