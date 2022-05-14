package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diabestes_care_app.Adapters.DoctorListAdapter;
import com.example.diabestes_care_app.Models.DoctorListModel;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_Fragment extends Fragment {

    // Firebase
    DatabaseReference myRef;
    // Widget
    RecyclerView recyclerView;
    // Variables
    ArrayList<DoctorListModel> list;
    // Adapter
    DoctorListAdapter doctorListAdapter;
    // Search Variables
    EditText searchInput;
    CharSequence search = "";
    TextView username;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_, container, false);

        //============================Defines=======================================================
        searchInput = view.findViewById(R.id.search_input);
        username = view.findViewById(R.id.Home_user_name);
        recyclerView = view.findViewById(R.id.recyclerView_HP);

        Intent intent_for_image = getActivity().getIntent();
        String patient_userName = intent_for_image.getStringExtra("username5");
        username.setText(patient_userName);
        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        // ArrayList
        list = new ArrayList<>();

        //============================Put data in Recyclerview======================================
        // Clear ArrayList
        ClearAll();
        // Get Data Method
        GetDataFromFirebase();
        //============================Search And Filter Function====================================
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doctorListAdapter.getFilter().filter(s);
                search = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    private void GetDataFromFirebase() {
        Query query = myRef.child("doctor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DoctorListModel doctorListModel = new DoctorListModel();
                    doctorListModel.setImageUrl(snapshot.child("personal_info").child("Image").child("mImageUrI").getValue().toString());
                    doctorListModel.setName(snapshot.child("personal_info").child("name_ar").getValue().toString());
                    doctorListModel.setUsername(snapshot.child("personal_info").child("username").getValue().toString());
                    list.add(doctorListModel);
                }
                doctorListAdapter = new DoctorListAdapter(getContext(), list);
                recyclerView.setAdapter(doctorListAdapter);
                doctorListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    private void ClearAll() {
        if (list != null) {
            list.clear();
        }
        if (doctorListAdapter != null) {
            doctorListAdapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }
}

