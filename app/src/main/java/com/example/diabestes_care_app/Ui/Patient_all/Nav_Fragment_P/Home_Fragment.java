package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.DoctorList_Adapter;
import com.example.diabestes_care_app.Models.DoctorList_Model;
import com.example.diabestes_care_app.Notification_Controller.Notification_Number;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Home_Fragment extends Fragment {

    // Firebase
    DatabaseReference myRef;
    // Widget
    RecyclerView recyclerView;
    // Variables
    ArrayList<DoctorList_Model> list;
    // Adapter
    DoctorList_Adapter doctorListAdapter;
    // Search Variables
    EditText searchInput;
    CharSequence search = "";
    TextView username;
    ImageView imageProfile;
    // Patient Username TextView
    String PatientUsername;
    // Notification Counter
    Notification_Number notification_number;
    // Progress Dialog
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_, container, false);

        //============================Defines=======================================================
        searchInput = view.findViewById(R.id.HP_search_input);
        username = view.findViewById(R.id.HP_patient_name);
        recyclerView = view.findViewById(R.id.HP_recyclerView);
        imageProfile = view.findViewById(R.id.HP_profile_img);

        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //============================Configure Firebase============================================
        myRef = FirebaseDatabase.getInstance().getReference();

        //==============================Progress Dialog=============================================
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("إنتظر قليلاً يتم تحميل المحتوى..");
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        progressDialog.show();

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
        // Get Patient Data Method
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
        //============================Notification Counter by using it Function=====================
        notification_number = new Notification_Number(view.findViewById(R.id.bell));
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_number.incrementNumber();
            }
        });
        return view;
    }

    //========================Get Doctor list Data From Firebase Function===========================
    private void GetDataFromFirebase() {
        Query query = myRef.child("doctor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DoctorList_Model doctorListModel = new DoctorList_Model();
                        doctorListModel.setName(snapshot.child("personal_info").child("name_ar").getValue().toString());
                        doctorListModel.setUsername(snapshot.child("personal_info").child("username").getValue().toString());
                        doctorListModel.setImageUrl(snapshot.child("personal_info").child("Image").child("mImageUrI").getValue().toString());
                        list.add(doctorListModel);
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "إنتظر قليلاً ", Toast.LENGTH_SHORT).show();
                }
                doctorListAdapter = new DoctorList_Adapter(getContext(), list );
                recyclerView.setAdapter(doctorListAdapter);
                doctorListAdapter.notifyDataSetChanged();
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
        if (doctorListAdapter != null) {
            doctorListAdapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }

    //============================Show The Patient name + image=====================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String image = snapshot.child(PatientUsername).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                    String name = snapshot.child(PatientUsername).child("personal_info").child("name").getValue(String.class);
                Glide.with(getActivity()).load(image).into(imageProfile);
                    Log.d("TAG", name + "/" + image);
                    username.setText(name);
                }
                // Mohammed SIam

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("TAG", error.getMessage());
                }
            });
    }

}

