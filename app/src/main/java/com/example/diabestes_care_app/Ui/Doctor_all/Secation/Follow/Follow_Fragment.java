package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Adapters.Patient_List_Adapter.MyPREFERENCES_P_List;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

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

import com.example.diabestes_care_app.Adapters.Doctor_Follow_Adapter;
import com.example.diabestes_care_app.Models.Follow_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Follow_Fragment extends Fragment {
    // Firebase
    DatabaseReference myRef, myRef2;
    // Widget
    RecyclerView recyclerView;
    // Variables
    List<Follow_Model> list;
    // Adapter
    Doctor_Follow_Adapter doctor_follow_adapter;
    // String
    String DoctorUsername, PatientUsername;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        recyclerView = view.findViewById(R.id.Recycle_Doctor_Follow);

        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef2 = FirebaseDatabase.getInstance().getReference("patient");

        SharedPreferences prefs2 = getContext().getSharedPreferences(MyPREFERENCES_P_List, MODE_PRIVATE);
        PatientUsername = prefs2.getString("TAG_NAME", null);

        Log.e("TAG", PatientUsername);

        SharedPreferences prefs = getContext().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        //============================Get Following from Doctor account=============================
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                Follow_Model follow_model;
                for (DataSnapshot sn : snapshot.child(DoctorUsername).child("Follow").getChildren()) {
                    follow_model = new Follow_Model();
                    follow_model.setImageUrl(PatientUsername);
//                    follow_model.setType(sn.getKey());
                    list.add(follow_model);
                }
                doctor_follow_adapter = new Doctor_Follow_Adapter(getContext(), list);
                recyclerView.setAdapter(doctor_follow_adapter);
                doctor_follow_adapter.updateUsersList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

////        ========================Get Patient  Data From Firebase Function==========================
//        myRef2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                ClearAll();
//                Follow_Model follow_model2;
//                try {
//                    follow_model2 = new Follow_Model();
////                        follow_model2.setImageUrl(dataSnapshot.child(PatientUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue().toString());
////                        follow_model2.setType(dataSnapshot.child(PatientUsername).child("disease_info").child("Diabetes Type").getValue().toString());
////                        list.add(follow_model2);
//
//                } catch (Exception e) {
//                    Log.e("TAG", e.getMessage());
//                }
//                doctor_follow_adapter = new Doctor_Follow_Adapter(getContext(), list);
//                recyclerView.setAdapter(doctor_follow_adapter);
//                doctor_follow_adapter.updateUsersList(list);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
        return view;
    }

    //    =====================================Function=================================================
    private void ClearAll() {
        if (list != null) {
            list.clear();
        }
        if (doctor_follow_adapter != null) {
            doctor_follow_adapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }
}