package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.example.diabestes_care_app.Ui.Sing_In.RestPassword_P.ResetPassword;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_1_P;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class LogIn_Patient_Fragment extends Fragment {

    // Firebase
    DatabaseReference DB_reference;
    // Username and Password
    EditText username, password2;
    // Login and Sing up
    Button login, SingUp;
    // Check Box rememberMe
    CheckBox rememberMe;
    // Object From Shared Preference
    SharedPreferences sharedpreferences;
    // Remember Check Box Shared Preference
    SharedPreferences Check_Box_preferences_P;
    public static final String MyPREFERENCES_P = "P_Username";

    // String
    String PatientToken;
    // Forget Password
    TextView forgetPassword;

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //============================ Inflate the layout for this fragment=========================
        View view = inflater.inflate(R.layout.fragment_log_in_p, container, false);
        //==============================Define======================================================
        login = view.findViewById(R.id.FSI_btn_login_P);
        username = view.findViewById(R.id.FSI_et_username_P);
        password2 = view.findViewById(R.id.FSI_et_pass_P);
        SingUp = view.findViewById(R.id.FSI_btn_Signup_P);
        rememberMe = view.findViewById(R.id.FSI_remember_CB_P);
        forgetPassword = view.findViewById(R.id.FSI_tv3_restore_pass_P);

        DB_reference = FirebaseDatabase.getInstance().getReference();
        //==============================Shared Preference===========================================
        sharedpreferences = this.requireActivity().getSharedPreferences(MyPREFERENCES_P, Context.MODE_PRIVATE);

        forgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ResetPassword.class);
            startActivity(intent);
        }

        );

        //============================== Remember Me Login  Patient ================================
        rememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()) {
                Check_Box_preferences_P = requireActivity().getSharedPreferences("checkbox_P", MODE_PRIVATE);
                SharedPreferences.Editor editor = Check_Box_preferences_P.edit();
                editor.putString("remember_P", "true");
                editor.apply();
                Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
            } else if (!buttonView.isChecked()) {
                Check_Box_preferences_P = requireActivity().getSharedPreferences("checkbox_P", MODE_PRIVATE);
                SharedPreferences.Editor editor = Check_Box_preferences_P.edit();
                editor.putString("remember_P", "false");
                editor.apply();
                Toast.makeText(getActivity(), "Un Checked", Toast.LENGTH_SHORT).show();
            }
        });

        //==============================Login Button================================================
        login.setOnClickListener(v -> {
            username.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            String patientEnterUsername = username.getText().toString();
            String patientEnterPassword = password2.getText().toString();

            DB_reference = FirebaseDatabase.getInstance().getReference("patient");
            Query checkUser = DB_reference.orderByChild("username").equalTo(patientEnterUsername);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.e("TAG", "onDataChange: " + snapshot.getValue());
                    // check if data exist
                    if (snapshot.exists()) {
                        username.setError(null);
                        // fit password to specific username
                        String passwordFromDB = snapshot.child(patientEnterUsername).child("Password").getValue(String.class);
                        assert passwordFromDB != null;
                        if (passwordFromDB.equals(patientEnterPassword)) {

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("TAG_NAME", patientEnterUsername);
                            editor.apply();
                            DB_reference.child(patientEnterUsername).child("Token").child("Patient_Token").setValue(PatientToken);
                            Intent intent = new Intent(getContext(), Home_Patient.class);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            password2.setError("Wrong Password");
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
        });

        //==============================Login Button================================================
        SingUp.setOnClickListener(v -> {
            Intent intentS = new Intent(getContext(), Sing_Up_1_P.class);
            startActivity(intentS);
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Generate Token for Patient
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                return;
            }
            // Get new FCM registration token
            PatientToken = task.getResult();
            System.out.println("TOKEN" + PatientToken);
        });
    }
}