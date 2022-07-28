package com.example.diabestes_care_app.Ui.Patient_all.Sections.Consulation;

import static com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor.MyPREFERENCES_D;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Adapters.Consultation_Adapter;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.Models.Consolation_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Consultation_Request extends Basic_Activity {

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
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_request);

        //============================Defines=======================================================
        recyclerView = findViewById(R.id.Consu_recycle);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = Consultation_Request.this.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        MSGKey = "0";
        //============================Configure Firebase============================================
        myRef = FirebaseDatabase.getInstance().getReference("Consultation request").child("MSG");
        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //============================Put data in Recyclerview======================================
        //ArrayList
        list = new ArrayList<>();
        // Clear ArrayList
        ClearAll();
        // Get Data Method
        GetDataFromFirebase();
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
                    Toast.makeText(Consultation_Request.this, "إنتظر قليلاً ", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", e.getMessage());
                }
                ConsuListAdapter = new Consultation_Adapter(Consultation_Request.this, list);
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