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

import com.example.diabestes_care_app.Adapters.Reports_Adapter;
import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Daily_Repo extends Fragment {

    Reports_Adapter reports_adapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Reports_Model> list;
    String PatientUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily__repo, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();

        Query query = databaseReference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
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
                    }
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });

        return view;
    }
}