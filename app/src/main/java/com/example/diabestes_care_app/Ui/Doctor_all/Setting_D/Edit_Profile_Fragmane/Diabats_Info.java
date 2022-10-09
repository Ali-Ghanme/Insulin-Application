package com.example.diabestes_care_app.Ui.Doctor_all.Setting_D.Edit_Profile_Fragmane;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    String DoctorUsername;
    // Data Patient
    TextView DiabetesType_t, DiabetesMedics_t, HaveIssue_t;
    Button update_btn;
    ProgressDialog progressDialog;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diabats_info_d, container, false);

        //============================Defines=======================================================
        DiabetesType_t = view.findViewById(R.id.FD_DiabetesType_d);
        DiabetesMedics_t = view.findViewById(R.id.FD_DiabetesMedics_d);
        HaveIssue_t = view.findViewById(R.id.FD_HaveIssue_d);
        update_btn = view.findViewById(R.id.button2_d);

        //============================Shared Preference=============================================
        SharedPreferences prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);
        myRef = FirebaseDatabase.getInstance().getReference("doctor").child(DoctorUsername).child("doctor_info");

        //============================Defines=======================================================
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("إنتظر قليلاً يتم تحديث البيانات..");
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        update_btn.setOnClickListener(v -> uploadData());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();

    }

    void getData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String DiabetesType = snapshot.child("العيادة الخارجية").getValue(String.class);
                String DiabetesMedics = snapshot.child("الشهادة الجامعية").getValue(String.class);
                String HaveIssue = snapshot.child("طبيعية العمل").getValue(String.class);
                DiabetesType_t.setText(DiabetesType);
                DiabetesMedics_t.setText(DiabetesMedics);
                HaveIssue_t.setText(HaveIssue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void uploadData() {
        progressDialog.show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String DType = DiabetesType_t.getText().toString();
                    String DMid = DiabetesMedics_t.getText().toString();
                    String DISu = HaveIssue_t.getText().toString();

                    myRef.child("العيادة الخارجية").setValue(DType);
                    myRef.child("الشهادة الجامعية").setValue(DMid);
                    myRef.child("طبيعية العمل").setValue(DISu);
                    Toast.makeText(getContext(), "Data is Updated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    getData();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}