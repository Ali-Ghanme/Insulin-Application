package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

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

public class LogIn_Patient_Fragment extends Fragment {

    DatabaseReference DB_reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText username, password2;
    Button login, SingUp;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Username";
    boolean password_is_visible;

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
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
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
                                String nameFromDB = snapshot.child(patientEnterUsername).child("personal_info").child("name").getValue(String.class);
                                String userNameFromDB = snapshot.child(patientEnterUsername).child("username").getValue(String.class);
                                String phoneFromDB = snapshot.child(patientEnterUsername).child("personal_info").child("Phone").getValue(String.class);
                                String ageFromDB = snapshot.child(patientEnterUsername).child("personal_info").child("Date").getValue(String.class);
                                String emailFromDB = snapshot.child(patientEnterUsername).child("personal_info").child("Email").getValue(String.class);
                                String NationalIDFromDB = snapshot.child(patientEnterUsername).child("personal_info").child("ID").getValue(String.class);
                                Log.d("TAG", nameFromDB + "/" + userNameFromDB + "/" + phoneFromDB);

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("TAG_NAME", patientEnterUsername);
                                editor.commit();
                                Intent intent = new Intent(getContext(), Home_Patient.class);
                                startActivity(intent);

                                Toast.makeText(getActivity(), "Hallow", Toast.LENGTH_SHORT).show();
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

        //============================Show password and hid password================================
        password2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Left = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password2.getLeft() - password2.getCompoundDrawables()[Left].getBounds().width()) {
                        showPass(password2);
                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

    public void showPass(EditText password) {
        int selection = password.getSelectionEnd();
        if (password_is_visible) {
            //set drawable image here
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_hide, 0);
            //for hide password
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password_is_visible = false;
        } else {
            //set drawable image here
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_show, 0);
            //for hide password
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password_is_visible = true;
        }
        password.setSelection(selection);
    }
}