package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

import static android.content.Context.MODE_PRIVATE;

import static com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapter.Patient_List_Adapter;
import com.example.diabestes_care_app.Models.PatientList_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Users_Notification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Home_Fragment_D extends Fragment {

    // Firebase
    DatabaseReference myRef;
    // Widget
    RecyclerView recyclerView;
    // Variables
    ArrayList<PatientList_Model> list;
    // Adapter
    Patient_List_Adapter patientList_adapter;
    // Search Variables
    SearchView searchInput;
    TextView username;
    ImageView imageProfile;
    // Patient Username TextView
    String DoctorUsername;
    // Notification Counter
    View bell;
    ProgressDialog progressDialog;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home___d, container, false);

        //============================Defines=======================================================
        searchInput = view.findViewById(R.id.HP_search_input_d);
        username = view.findViewById(R.id.HP_patient_name_d);
        recyclerView = view.findViewById(R.id.HP_recyclerView_d);
        imageProfile = view.findViewById(R.id.HP_profile_img_d);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("إنتظر قليلاً يتم تحميل المحتوى..");
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        progressDialog.show();

        bell = view.findViewById(R.id.bell_D);

        bell.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Users_Notification.class);
            startActivity(intent);
        });
        //============================Firebase======================================================
        myRef = FirebaseDatabase.getInstance().getReference();

        //============================Get Doctor Username===========================================
        SharedPreferences prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //============================ArrayList=====================================================
        list = new ArrayList<>();
        // Clear ArrayList
        ClearAll();
        // Get Patient Data Method
        GetDataFromFirebase();
        // Search Filter
        searchInput.setQueryHint("إبحث عن مرضى");

        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        return view;
    }

    //========================Get Patient list Data From Firebase Function===========================
    private void GetDataFromFirebase() {
        Query query = myRef.child("patient");
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PatientList_Model patientListModel = new PatientList_Model();
                        patientListModel.setName(Objects.requireNonNull(snapshot.child("personal_info").child("name").getValue()).toString());
                        patientListModel.setUsername(Objects.requireNonNull(snapshot.child("username").getValue()).toString());
                        patientListModel.setPatientType(Objects.requireNonNull(snapshot.child("disease_info").child("Diabetes Type").getValue()).toString());
                        patientListModel.setImageUrl(Objects.requireNonNull(snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue()).toString());
                        patientListModel.setToken(Objects.requireNonNull(snapshot.child("Token").child("Patient_Token").getValue()).toString());
                        list.add(patientListModel);
                        progressDialog.dismiss();
                    }

                } catch (Exception ignored) {

                }
                patientList_adapter = new Patient_List_Adapter(getContext(), list);
                recyclerView.setAdapter(patientList_adapter);
                patientList_adapter.updateUsersList(list);
                patientList_adapter.notifyDataSetChanged();
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
        if (patientList_adapter != null) {
            patientList_adapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }

    //============================Show The doctor name + image======================================
    @Override
    public void onStart() {
        super.onStart();

        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                String image = snapshot.child(DoctorUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                String name = snapshot.child(DoctorUsername).child("personal_info").child("name_ar").getValue(String.class);
                Glide.with(getActivity()).load(image).into(imageProfile);
                Log.d("TAG", name + "/" + image);
                username.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    //==================================Search Method===============================================
    private void FilterList(String newText) {
        ArrayList<PatientList_Model> filteredList = new ArrayList<>();
        for (PatientList_Model item : list) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            } else if (item.getPatientType().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "لا يوجد بيانات بهذا العنوان", Toast.LENGTH_SHORT).show();
        } else {
            patientList_adapter.setFilteredList(filteredList);
        }
    }
}