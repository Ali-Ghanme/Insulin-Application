package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import static android.content.Context.MODE_PRIVATE;

public class LogIn_Doctor_Fragment extends Fragment {

    DatabaseReference DB_reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText username, password;
    Button login, SingUp;
    TextView name;
    SharedPreferences sharedpreferences;


    CheckBox remember;


    public static final String MyPREFERENCES_D = "D_Username";
    SharedPreferences preferences_D;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //============================ Inflate the layout for this fragment=========================
        View view = inflater.inflate(R.layout.fragment_log_in_d, container, false);

        //==============================Casting=====================================================
        login = view.findViewById(R.id.FIS_bt_D);
        username = view.findViewById(R.id.FIS_et_username_D);
        remember = view.findViewById(R.id.rememberMy);
        password = view.findViewById(R.id.FIS_et_pass_D);
        name = view.findViewById(R.id.FSI_tv3_show_P);
        SingUp = view.findViewById(R.id.FIS_bt_sing_up_D);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);

        //==============================Remember Me Login Doctor================================================
        SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if (checkbox.equals("true")){
            Intent intent = new Intent(getActivity(), Home_Doctor.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Un Checked", Toast.LENGTH_SHORT).show();

        }else if (checkbox.equals("false")){
            Toast.makeText(getActivity(), " Please Sign In", Toast.LENGTH_SHORT).show();
        }
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

        //==============================Remember Me Login Doctor================================================

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    preferences_D = getActivity().getSharedPreferences("checkbox_D", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences_D.edit();
                    editor.putString("remember_D", "true");
                    editor.apply();
                    Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
                } else if (!buttonView.isChecked()) {
                     preferences_D = getActivity().getSharedPreferences("checkbox_D", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences_D.edit();
                    editor.putString("remember_D", "false");
                    editor.apply();
                    Toast.makeText(getActivity(), "Un Checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //==============================END Remember Me Login Doctor ================================================

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
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("TAG_NAME", patientEnterUsername);
                        editor.commit();

                        Intent intent = new Intent(getActivity(), Home_Doctor.class);
                        startActivity(intent);
                        getActivity().finish();

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
    // Hallow this is update

}