package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Adapter.Doctor_Follow_Adapter.MyPREFERENCES_P_Username_D;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Dwree_Report_Fragment extends Fragment {

    TextView result_creatinine, result_urea, result_uric, result_cholesterol, result_triglycerid,
            result_ldl, result_hdl, result_pressures, result_bmi_height, result_bmi_weight, bim;

    DatabaseReference databaseReference;
    String getName;
    LinearLayout no_Data;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dwree_report, container, false);
        result_creatinine = view.findViewById(R.id.FDR_result_creatinine);
        result_urea = view.findViewById(R.id.FDR_result_urea);
        result_uric = view.findViewById(R.id.FDR_result_uric);
        result_cholesterol = view.findViewById(R.id.FDR_result_cholesterol);
        result_triglycerid = view.findViewById(R.id.FDR_result_triglycerid);
        result_ldl = view.findViewById(R.id.FDR_result_ldl);
        result_hdl = view.findViewById(R.id.FDR_result_hdl);
        result_pressures = view.findViewById(R.id.FDR_result_pressure);
        result_bmi_height = view.findViewById(R.id.FDR_result_bmi_height);
        result_bmi_weight = view.findViewById(R.id.FDR_result_bmi_weight);
        bim = view.findViewById(R.id.FDR_result_bmi);

        no_Data = view.findViewById(R.id.No_Data_d_2);
        scrollView = view.findViewById(R.id.scrollable_view);


        //=====================Get data from message adapter class==================================
        SharedPreferences prefs = requireActivity().getSharedPreferences(MyPREFERENCES_P_Username_D, MODE_PRIVATE);
        getName = prefs.getString("PatientUsername_D", null);

        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(getName).child("Reports_info").child("فحوصات دورية");
        getUserData();
        return view;
    }

    void getUserData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        result_creatinine.setText(Objects.requireNonNull(snapshot.child("فحوصات وظائف الكلى").child("Creatine").getValue()).toString());
                        result_urea.setText(Objects.requireNonNull(snapshot.child("فحوصات وظائف الكلى").child("Urea").getValue()).toString());
                        result_uric.setText(Objects.requireNonNull(snapshot.child("فحوصات وظائف الكلى").child("Uric").getValue()).toString());
                        result_cholesterol.setText(Objects.requireNonNull(snapshot.child("فحوصات الدهون").child("Cholesterol").getValue()).toString());
                        result_triglycerid.setText(Objects.requireNonNull(snapshot.child("فحوصات الدهون").child("HDL").getValue()).toString());
                        result_ldl.setText(Objects.requireNonNull(snapshot.child("فحوصات الدهون").child("IDL").getValue()).toString());
                        result_hdl.setText(Objects.requireNonNull(snapshot.child("فحوصات الدهون").child("Triglyceride").getValue()).toString());
                        result_pressures.setText(Objects.requireNonNull(snapshot.child("فحص ضغط الدم").child("Pressure").getValue()).toString());
                        result_bmi_height.setText(Objects.requireNonNull(snapshot.child("فحوصات مؤشر كتلة الجسم").child("bmi_height").getValue()).toString());
                        result_bmi_weight.setText(Objects.requireNonNull(snapshot.child("فحوصات مؤشر كتلة الجسم").child("bmi_weight").getValue()).toString());
                        bim.setText(Objects.requireNonNull(snapshot.child("فحوصات مؤشر كتلة الجسم").child("مؤشر كتلة الجسم").getValue()).toString());
                        no_Data.setVisibility(View.INVISIBLE);
                        scrollView.setVisibility(View.VISIBLE);
                    } else {
                        no_Data.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.INVISIBLE);

                    }
                } catch (Exception ignored) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
}