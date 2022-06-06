package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.MainActivity;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Home_Fragment;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_5_P;
import com.example.diabestes_care_app.Ui.Sing_up_pages.character_choice_screen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LogIn_Patient_Fragment extends Fragment {

    DatabaseReference DB_reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText username, password2;
    Button login, SingUp;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES_P = "P_Username";
    CheckBox rememberMe;
    SharedPreferences preferences_P;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //============================ Inflate the layout for this fragment=========================
        View view = inflater.inflate(R.layout.fragment_log_in_p, container, false);
        //==============================Casting=====================================================
        login = view.findViewById(R.id.FSI_btn_login_P);
        username = view.findViewById(R.id.FSI_et_username_P);
        password2 = view.findViewById(R.id.FSI_et_pass_P);
        SingUp = view.findViewById(R.id.FSI_btn_Signup_P);
        rememberMe = view.findViewById(R.id.FSI_remember_CB_P);

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES_P, Context.MODE_PRIVATE);

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    preferences_P = getActivity().getSharedPreferences("checkbox_P", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences_P.edit();
                    editor.putString("remember_P", "true");
                    editor.apply();
                    Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
                } else if (!buttonView.isChecked()) {
                    preferences_P = getActivity().getSharedPreferences("checkbox_P", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences_P.edit();
                    editor.putString("remember_P", "false");
                    editor.apply();
                    Toast.makeText(getActivity(), "Un Checked", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //==============================Login Button================================================
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patientEnterUsername = username.getText().toString();
                String patientEnterPassword = password2.getText().toString();

                DB_reference = FirebaseDatabase.getInstance().getReference("patient");
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

                                Intent intent = new Intent(getContext(), Home_Patient.class);
                                startActivity(intent);
                                getActivity().finish();
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
            }
        });

        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentS = new Intent(getContext(), character_choice_screen.class);
                startActivity(intentS);
            }
        });

        return view;
    }
}
// Hallow this is update
