package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Consulation;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor.MyPREFERENCES_D;

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

import com.example.diabestes_care_app.Adapters.Consultation_Adapter;
import com.example.diabestes_care_app.Models.Consolation_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class General_Fragment extends Fragment {
    // Firebase
    DatabaseReference myRef;
    // Widget
    RecyclerView recyclerView;
    // Variables
    ArrayList<Consolation_Model> list;
    // Adapter
    Consultation_Adapter ConsuListAdapter;
    // Doctor Username TextView
    String DoctorUsername, MSGKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genral_, container, false);

        //============================Defines=======================================================
        recyclerView = view.findViewById(R.id.Consu_recycle_general);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = getContext().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        MSGKey = "0";
        //============================Configure Firebase============================================
        myRef = FirebaseDatabase.getInstance().getReference("Consultation request").child("MSG");
        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //============================Put data in Recyclerview======================================
        //ArrayList
        list = new ArrayList<>();
        // Clear ArrayList
        ClearAll();
        // Get Data Method
        GetDataFromFirebase();
        return view;
    }

    //========================Get Doctor list Data From Firebase Function===========================
    private void GetDataFromFirebase() {
        Query query = myRef.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Consolation_Model consolation_model = new Consolation_Model();
                        consolation_model.setTitle(snapshot.child("Title").getValue().toString());
                        consolation_model.setDoctorName(snapshot.child("to").getValue().toString());
                        consolation_model.setQuestion(snapshot.child("Subject").getValue().toString());
                        consolation_model.setImageUrl(snapshot.child("Doctor_Profile").getValue().toString());
                        list.add(consolation_model);
                        Log.e("TAG", MSGKey);
                    }
                } catch (Exception e) {
                    Log.e("TAG", e.getMessage());
                }
                ConsuListAdapter = new Consultation_Adapter(getContext(), list);
                recyclerView.setAdapter(ConsuListAdapter);
                ConsuListAdapter.updateUsersList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    //============================Clear Recycle Review Data Function================================
    private void ClearAll() {
        if (list != null) {
            list.clear();
        }
        if (ConsuListAdapter != null) {
            ConsuListAdapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }
}
