package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Adapters.Doctor_Follow_Adapter.MyPREFERENCES_P_Username_D;

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


public class Dwree_Report_Fragment extends Fragment {

    TextView result_creatinine, result_urea, result_uric, result_cholesterol, result_triglycerid,
            result_ldl, result_hdl, result_pressures, result_bmi_height, result_bmi_weight,bim;

    DatabaseReference databaseReference;
    String getName;

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


        // Get data from message adapter class
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES_P_Username_D, MODE_PRIVATE);
        getName = prefs.getString("PatientUsername_D", null);

        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(getName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                result_creatinine.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات وظائف الكلى").child("Creatine").getValue().toString());
                result_urea.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات وظائف الكلى").child("Urea").getValue().toString());
                result_uric.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات وظائف الكلى").child("Uric").getValue().toString());

                result_cholesterol.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("Cholesterol").getValue().toString());
                result_triglycerid.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("HDL").getValue().toString());
                result_ldl.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("IDL").getValue().toString());
                result_hdl.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات الدهون").child("Triglyceride").getValue().toString());

                result_pressures.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحص ضغط الدم").child("Pressure").getValue().toString());

                result_bmi_height.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات مؤشر كتلة الجسم").child("bmi_height").getValue().toString());
                result_bmi_weight.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات مؤشر كتلة الجسم").child("bmi_weight").getValue().toString());
                bim.setText(snapshot.child("Reports_info").child("فحوصات دورية").child("فحوصات مؤشر كتلة الجسم").child("مؤشر كتلة الجسم").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}