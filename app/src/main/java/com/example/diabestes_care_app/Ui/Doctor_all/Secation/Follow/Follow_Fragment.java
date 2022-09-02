package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    DatabaseReference myRef;
    // Widget
    RecyclerView recyclerView;
    // Variables
    List<Follow_Model> list;
    // Adapter
    Doctor_Follow_Adapter doctor_follow_adapter;
    // String
    String DoctorUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        recyclerView = view.findViewById(R.id.Recycle_Doctor_Follow);

        SharedPreferences prefs = getContext().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);
        myRef = FirebaseDatabase.getInstance().getReference("doctor");

        // ArrayList
        list = new ArrayList<>();
        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ClearAll();
        GetFollowingPatient();
        return view;
    }

    //======================================Function================================================
    //Get Following from Doctor account
    private void GetFollowingPatient(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                Follow_Model follow_model;
                for (DataSnapshot sn : snapshot.child(DoctorUsername).child("Follow").getChildren()) {
                    follow_model = new Follow_Model();
                    follow_model.setUsername(sn.getKey());
                    follow_model.setType(sn.getKey());
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
    }

    private void ClearAll() {
        if (list != null) {
            list.clear();
        }else {
            Toast.makeText(getContext(), "The List is null", Toast.LENGTH_SHORT).show();
        }
        if (doctor_follow_adapter != null) {
            doctor_follow_adapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }
}