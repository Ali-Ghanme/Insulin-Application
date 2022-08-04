package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Repo_View;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Adapters.Reports_Monthly_Adapter;
import com.example.diabestes_care_app.Models.Reports_Monthly_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Dwree_Repo extends Fragment {

    // Adapter
    Reports_Monthly_Adapter reports_monthly_adapter;
    // Recycle view
    RecyclerView recyclerViewMonthly;
    // DB Ref
    DatabaseReference databaseReference;
    // Arraylist
    ArrayList<Reports_Monthly_Model> list2;
    // Patient Username
    String PatientUsername;
    // Mohammed Siam
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dwree__repo, container, false);

        recyclerViewMonthly = view.findViewById(R.id.recyclerViewMonthly);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMonthly.setLayoutManager(layoutManager);
        recyclerViewMonthly.setHasFixedSize(true);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        list2 = new ArrayList<>();
        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    Reports_Monthly_Model reports_monthly_model = new Reports_Monthly_Model();

                    // فحوصات الدهون
                    reports_monthly_model.setResult_cholesterol(dataSnapshot.child("فحوصات الدهون").child("Cholesterol").getValue().toString());
                    reports_monthly_model.setResult_hdl(dataSnapshot.child("فحوصات الدهون").child("HDL").getValue().toString());
                    reports_monthly_model.setResult_ldl(dataSnapshot.child("فحوصات الدهون").child("IDL").getValue().toString());
                    reports_monthly_model.setResult_triglycerid(dataSnapshot.child("فحوصات الدهون").child("Triglyceride").getValue().toString());

                    // فحوصات  وظائف الكلى
                    reports_monthly_model.setResult_urea(dataSnapshot.child("فحوصات وظائف الكلى").child("Urea").getValue().toString());
                    reports_monthly_model.setResult_creatinine(dataSnapshot.child("فحوصات وظائف الكلى").child("Creatine").getValue().toString());
                    reports_monthly_model.setResult_uric(dataSnapshot.child("فحوصات وظائف الكلى").child("Uric").getValue().toString());

                    // فحوصات مؤشر الكتلة
                    reports_monthly_model.setResult_bmi_height(dataSnapshot.child("فحوصات مؤشر كتلة الجسم").child("bmi_height").getValue().toString());
                    reports_monthly_model.setResult_bmi_weight(dataSnapshot.child("فحوصات مؤشر كتلة الجسم").child("bmi_weight").getValue().toString());

                    // فحص ضغط الدم
                    reports_monthly_model.setResult_presser(dataSnapshot.child("فحص ضغط الدم").child("Pressure").getValue().toString());

                    list2.add(reports_monthly_model);

                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
                reports_monthly_adapter = new Reports_Monthly_Adapter(getContext(), list2);
                recyclerViewMonthly.setAdapter(reports_monthly_adapter);
                reports_monthly_adapter.updateUsersList(list2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });

        return view;
    }
}