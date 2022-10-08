package com.example.diabestes_care_app.Ui.Patient_all.Sections.Consulation;

import static com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
    // Serach View
    SearchView searchView;
    // Image View
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_request);

        //============================Defines=======================================================
        recyclerView = findViewById(R.id.Consu_recycle);
        searchView = findViewById(R.id.search_bar);
        imageView = findViewById(R.id.DPP_btn_back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        // Search Filter
        searchView.setQueryHint("أتبحث عن سؤال معين ؟");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FilterList(newText);
                return true;
            }
        });
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
                        consolation_model.setQuestion(snapshot.child("Subject").getValue().toString());
                        consolation_model.setAnswer(snapshot.child("Doctor_Answer").getValue().toString());
                        consolation_model.setDoctorName(snapshot.child("to").getValue().toString());
                        consolation_model.setImageUrl(snapshot.child("Doctor_Image").getValue().toString());
                        list.add(consolation_model);
                        Log.e("TAG", MSGKey);
                    }
                } catch (Exception e) {
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
    @SuppressLint("NotifyDataSetChanged")
    private void ClearAll() {
        if (list != null) {
            list.clear();
        }
        if (ConsuListAdapter != null) {
            ConsuListAdapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }

    //==================================Search Method===============================================
    private void FilterList(String newText) {
        ArrayList<Consolation_Model> filteredList = new ArrayList<>();
        for (Consolation_Model item : list) {
            if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            } else if (item.getQuestion().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            } else if (item.getDoctorName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            ConsuListAdapter.setFilteredList(filteredList);
        }
    }
}
// Hallow this is Update