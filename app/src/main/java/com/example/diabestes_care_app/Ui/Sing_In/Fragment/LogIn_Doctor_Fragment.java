package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor.Sing_Up_1_D;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class LogIn_Doctor_Fragment extends Fragment {

    // Firebase
    DatabaseReference DB_reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    // username , password
    EditText username, password;
    // login and Signup
    Button login, SingUp;
    // Shared Preference
    SharedPreferences sharedpreferences;
    // Remember Me Check Box
    CheckBox remember;
    // Doctor User name Preference
    public static final String MyPREFERENCES_D = "D_Username";
    // Shared Preference For Remember me Check Box
    SharedPreferences Check_Box_preferences_D;
    // Token
    String DoctorToken;

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
        SingUp = view.findViewById(R.id.FIS_bt_sing_up_D);

        //==============================Shared Preference===========================================
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);

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
                Intent intentS = new Intent(getContext(), Sing_Up_1_D.class);
                startActivity(intentS);
            }
        });

        //==============================Remember Me Login Doctor================================================
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Check_Box_preferences_D = getActivity().getSharedPreferences("checkbox_D", MODE_PRIVATE);
                    SharedPreferences.Editor editor = Check_Box_preferences_D.edit();
                    editor.putString("remember_D", "true");
                    editor.apply();
                    Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
                } else if (!buttonView.isChecked()) {
                    Check_Box_preferences_D = getActivity().getSharedPreferences("checkbox_D", MODE_PRIVATE);
                    SharedPreferences.Editor editor = Check_Box_preferences_D.edit();
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
        String doctorEnterUsername = username.getText().toString();
        String doctorEnterPassword = password.getText().toString();

        DB_reference = FirebaseDatabase.getInstance().getReference("doctor");
        Query checkUser = DB_reference.orderByChild("username").equalTo(doctorEnterUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DB_reference.child(doctorEnterUsername).child("Token").child("Doctor_Token").setValue(DoctorToken);
                // check if data exist
                if (snapshot.exists()) {
                    username.setError(null);
                    // fit password to specific username
                    String passwordFromDB = snapshot.child(doctorEnterUsername).child("Password").getValue(String.class);
                    if (passwordFromDB.equals(doctorEnterPassword)) {

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("TAG_NAME", doctorEnterUsername);
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

    @Override
    public void onStart() {
        super.onStart();
        // Generate Token for Patient
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                // Get new FCM registration token
                DoctorToken = task.getResult();
                System.out.println("TOKEN" + DoctorToken);
            }
        });
    }
}