package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Consulation;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.annotation.SuppressLint;
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

import com.example.diabestes_care_app.Adapter.Response_Consu_Adapter;
import com.example.diabestes_care_app.Models.Private_Consu_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Privet_Cons extends Fragment {
    // Firebase
    DatabaseReference myRef;
    // Widget
    RecyclerView recyclerView;
    // Variables
    ArrayList<Private_Consu_Model> list;
    // Adapter
    Response_Consu_Adapter response_consu_adapter;
    // DoctorUsername
    String DoctorUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privet__cons, container, false);

        recyclerView = view.findViewById(R.id.response_recycleView);
        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        SharedPreferences prefs = requireActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================ArrayList=====================================================
        list = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("doctor").child(DoctorUsername).child("Consultation request").child("MSG");
        Query query = myRef.orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        Private_Consu_Model private_consu_model = new Private_Consu_Model();
                        private_consu_model.setPatientName(Objects.requireNonNull(sn.child("from").getValue()).toString());
                        private_consu_model.setDoctor_name(Objects.requireNonNull(sn.child("to").getValue()).toString());
                        private_consu_model.setConsuSubject(Objects.requireNonNull(sn.child("Subject").getValue()).toString());
                        private_consu_model.setPatientImage(Objects.requireNonNull(sn.child("Patient_Profile").getValue()).toString());
                        private_consu_model.setConsuTitle(Objects.requireNonNull(sn.child("Title").getValue()).toString());
                        private_consu_model.setDoctorImage(Objects.requireNonNull(sn.child("Doctor_Profile").getValue()).toString());
                        private_consu_model.setDoctorAnswer(Objects.requireNonNull(sn.child("Doctor_Answer").getValue()).toString());
                        private_consu_model.setPushKey(Objects.requireNonNull(sn.child("PushKey").getValue()).toString());
                        private_consu_model.setPatientToken(Objects.requireNonNull(sn.child("PatientToken").getValue()).toString());
                        list.add(private_consu_model);
                    }
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
                response_consu_adapter = new Response_Consu_Adapter(getContext(), list);
                recyclerView.setAdapter(response_consu_adapter);
                response_consu_adapter.updateUsersList(list);
                response_consu_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });

        return view;
    }
}