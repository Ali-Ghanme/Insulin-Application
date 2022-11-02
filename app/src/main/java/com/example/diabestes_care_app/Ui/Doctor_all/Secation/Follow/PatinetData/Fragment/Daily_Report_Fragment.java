package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Adapter.Doctor_Follow_Adapter.MyPREFERENCES_P_Username_D;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Adapter.Reports_Adapter;
import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Daily_Report_Fragment extends Fragment {

    Reports_Adapter reports_adapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Reports_Model> list;
    String PatientUsername;
    LinearLayout No_Data;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_report, container, false);

        //===================================Define=================================================
        recyclerView = view.findViewById(R.id.Daily_report_recyclerView);
        No_Data = view.findViewById(R.id.No_Data_d);

        //============================Get Patient Username===========================================
        // Get data from message adapter class
        SharedPreferences prefs = requireActivity().getSharedPreferences(MyPREFERENCES_P_Username_D, MODE_PRIVATE);
        PatientUsername = prefs.getString("PatientUsername_D", null);

        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        getUserData();


        return view;
    }

    void getUserData() {
        Query query = databaseReference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Reports_Model reportsModel = new Reports_Model();
                            reportsModel.setTimeSugar(snapshot.child("فترة القياس").getValue().toString());
                            reportsModel.setTime(snapshot.child("وقت القياس").getValue().toString());
                            reportsModel.setBloodSugar(snapshot.child("نسبة السكر في الدم").getValue().toString());
                            reportsModel.setStatusSugar(snapshot.child("حالة القياس").getValue().toString());
                            list.add(reportsModel);
                            reports_adapter = new Reports_Adapter(getContext(), list);
                            recyclerView.setAdapter(reports_adapter);
                            reports_adapter.updateUsersList(list);
                            No_Data.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), "لا يوجد بيانات بعد يومي", Toast.LENGTH_SHORT).show();
                        No_Data.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
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