package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports.Repo_View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Adapters.Reports_Adapter;
import com.example.diabestes_care_app.Adapters.Reports_Monthly_Adapter;
import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.Models.Reports_Monthly_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;


public class Dwree_Repo extends Fragment {
    Reports_Monthly_Adapter reports_monthly_adapter ;
    RecyclerView recyclerViewMonthly;
    DatabaseReference databaseReference;
    ArrayList<Reports_Monthly_Model> list2;
    String PatientUsername;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dwree__repo, container, false);

        recyclerViewMonthly = view.findViewById(R.id.recyclerViewMonthly);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحوصات دورية");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewMonthly.setLayoutManager(layoutManager);
        recyclerViewMonthly.setHasFixedSize(true);
        list2 = new ArrayList<>();

        Query query = databaseReference;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Reports_Monthly_Model reports_monthly_model = new Reports_Monthly_Model();
                        reports_monthly_model.setResult_pressuer(snapshot.child("فحص ضغط الدم").child("Pressure").getValue().toString());

                        reports_monthly_model.setResult_cholesterol(snapshot.child("فحوصات الدهون").child("Cholesterol").getValue().toString());
                        reports_monthly_model.setResult_hdl(snapshot.child("فحوصات الدهون").child("HDL").getValue().toString());
                        reports_monthly_model.setResult_ldl(snapshot.child("فحوصات الدهون").child("IDL").getValue().toString());
                        reports_monthly_model.setResult_triglycerid(snapshot.child("فحوصات الدهون").child("Triglyceride").getValue().toString());

                        reports_monthly_model.setResult_bmi_height(snapshot.child("فحوصات مؤشر كتلة الجسم").child("bmi_height").getValue().toString());
                        reports_monthly_model.setResult_bmi_weight(snapshot.child("فحوصات مؤشر كتلة الجسم").child("bmi_weight").getValue().toString());

                        reports_monthly_model.setResult_creatinine(snapshot.child("فحوصات وظائف الكلى").child("Creatine").getValue().toString());
                        reports_monthly_model.setResult_urea(snapshot.child("فحوصات وظائف الكلى").child("Urea").getValue().toString());
                        reports_monthly_model.setResult_uric(snapshot.child("فحوصات وظائف الكلى").child("Uric").getValue().toString());

                        list2.add(reports_monthly_model);

                        Log.e("rmr", String.valueOf(reports_monthly_model));
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "تأكد من وجود الإنترنت أو قم بإدخال فحوصات", Toast.LENGTH_SHORT).show();
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

        return view ;
    }
}