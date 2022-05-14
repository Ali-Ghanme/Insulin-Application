package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Doctor_Fragment extends Fragment {

    DatabaseReference DB_reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText username, password;
    Button login, SingUp;
    TextView name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //============================ Inflate the layout for this fragment=========================
        View view = inflater.inflate(R.layout.fragment_log_in_d, container, false);

        //==============================Casting=====================================================
        login = view.findViewById(R.id.FIS_bt_D);
        username = view.findViewById(R.id.FIS_et_username_D);
        password = view.findViewById(R.id.FIS_et_pass_D);
        name = view.findViewById(R.id.FSI_tv3_show_P);
        SingUp = view.findViewById(R.id.FIS_bt_sing_up_D);

        //==============================Login Button================================================
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });
        //==============================Sing Button=================================================
        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sing Up Success", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void isUser() {
        String patientEnterUsername = username.getText().toString();
        String patientEnterPassword = password.getText().toString();

        DB_reference = FirebaseDatabase.getInstance().getReference("doctor");
        Query checkUser = DB_reference.orderByChild("username").equalTo(patientEnterUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check if data exist
                if (snapshot.exists()) {
                    username.setError(null);
                    // fit password to specific username
                    String passwordFromDB = snapshot.child(patientEnterUsername).child("Password").getValue(String.class);
                    if (passwordFromDB.equals(patientEnterPassword)) {
                        Intent intent = new Intent(getActivity(), Home_Doctor.class);
                        startActivity(intent);

                    } else {
                        password.setError("Wrong Password");
                    }
                } else {
                    username.setError("No Such User exits");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
}