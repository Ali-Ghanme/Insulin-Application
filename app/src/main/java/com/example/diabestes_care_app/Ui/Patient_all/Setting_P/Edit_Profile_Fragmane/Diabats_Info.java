package com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Diabats_Info extends Fragment {

    // Firebase
    DatabaseReference myRef;
    // Patient Username
    String PatientUsername;
    // Data Patient
    TextView DiabetesType_t, DiabetesMedics_t, PatientCauses_t, bloodGlucoseMeter_t, HaveIssue_t, DateInjury_t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diabats__info, container, false);

        //============================Defines=======================================================
        DiabetesType_t = view.findViewById(R.id.FD_DiabetesType);
        DiabetesMedics_t = view.findViewById(R.id.FD_DiabetesMedics);
        PatientCauses_t = view.findViewById(R.id.FD_PatientCauses);
        bloodGlucoseMeter_t = view.findViewById(R.id.FD_bloodGlucoseMeter);
        HaveIssue_t = view.findViewById(R.id.FD_HaveIssue);
        DateInjury_t = view.findViewById(R.id.FD_DateInjury);

        //============================Shared Preference=============================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String DiabetesType = snapshot.child(PatientUsername).child("disease_info").child("Diabetes Type").getValue(String.class);
                String DiabetesMedics = snapshot.child(PatientUsername).child("disease_info").child("Diabetes Medics Type").getValue(String.class);
                String PatientCauses = snapshot.child(PatientUsername).child("disease_info").child("Patient Causes").getValue(String.class);
                String bloodGlucoseMeter = snapshot.child(PatientUsername).child("disease_info").child("Does the patient have a blood glucose meter?").getValue(String.class);
                String HaveIssue = snapshot.child(PatientUsername).child("disease_info").child("Have issue").getValue(String.class);
                String DateInjury = snapshot.child(PatientUsername).child("disease_info").child("Patient Date Injury").getValue(String.class);
                DiabetesType_t.setText(DiabetesType);
                DiabetesMedics_t.setText(DiabetesMedics);
                PatientCauses_t.setText(PatientCauses);
                bloodGlucoseMeter_t.setText(bloodGlucoseMeter);
                HaveIssue_t.setText(HaveIssue);
                DateInjury_t.setText(DateInjury);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}