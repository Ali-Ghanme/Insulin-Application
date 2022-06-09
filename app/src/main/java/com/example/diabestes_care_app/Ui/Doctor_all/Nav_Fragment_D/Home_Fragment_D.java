package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.DoctorListAdapter;
import com.example.diabestes_care_app.Models.DoctorListModel;
import com.example.diabestes_care_app.Notification_Controller.Notification_Number;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home_Fragment_D extends Fragment {
    // implements Interface_Recycle
    FirebaseDatabase db;
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
    ImageView imageProfile;
    // ShardPreference
    public static final String MyPREFERENCES_P = "D_Username";
    // Patient Username TextView
    String restoredText;
    // Notification Counter
    Notification_Number notification_number;

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
        //============================Get Doctor Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //============================Configure Recyclerview========================================
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        // Firebase
        myRef = FirebaseDatabase.getInstance().getReference();

        // الفنكشن الخاصة بحالة المستخدم
        db = FirebaseDatabase.getInstance();
        manageConnections();

        // ArrayList
        list = new ArrayList<>();
        //============================Put data in Recyclerview======================================
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
        Query query = myRef.child("patient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DoctorListModel doctorListModel = new DoctorListModel();
                        doctorListModel.setName(snapshot.child("personal_info").child("name").getValue().toString());
                        doctorListModel.setUsername(snapshot.child("username").getValue().toString());
                        doctorListModel.setImageUrl(snapshot.child("personal_info").child("Image").child("mImageUrI").getValue().toString());
                        list.add(doctorListModel);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "إنتظر قليلاً ", Toast.LENGTH_SHORT).show();
                }
                doctorListAdapter = new DoctorListAdapter(getContext(), list );
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

    //============================Show The doctor name + image======================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.child(restoredText).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                String name = snapshot.child(restoredText).child("personal_info").child("name_ar").getValue(String.class);
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

    //============================Show The doctor name + image======================================
    private void manageConnections() {
        final DatabaseReference connectReference = db.getReference().child("connections");
        final DatabaseReference lastConnected = db.getReference().child("lastConnected");
        final DatabaseReference infoConnected = db.getReference(".info/connected");
        infoConnected.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(boolean.class);
                if (connected) {
                    DatabaseReference con = connectReference.child("doctor");
                    con.setValue(ServerValue.TIMESTAMP);
                    con.onDisconnect().setValue(false);
                    lastConnected.onDisconnect().setValue(ServerValue.TIMESTAMP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
    // مشان يا الله يا غزة يلا
    // Hallow this is update

}