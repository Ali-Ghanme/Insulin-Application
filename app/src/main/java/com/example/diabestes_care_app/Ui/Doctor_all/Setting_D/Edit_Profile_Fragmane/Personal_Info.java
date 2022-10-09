package com.example.diabestes_care_app.Ui.Doctor_all.Setting_D.Edit_Profile_Fragmane;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Personal_Info extends Fragment {

    DatabaseReference databaseReference;

    String restoredText;
    EditText Phone_number_t, email_t, address_t, password, coPassword;
    Button update_btn;
    SharedPreferences prefs;
    ProgressDialog progressDialog;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__info_d, container, false);
        //============================Defines=======================================================
        Phone_number_t = view.findViewById(R.id.FD_Phone_number_d);
        email_t = view.findViewById(R.id.FD_email_d);
        address_t = view.findViewById(R.id.FD_address_d);
        update_btn = view.findViewById(R.id.Update_btn_d);
        password = view.findViewById(R.id.FD_passwordRest_d);
        coPassword = view.findViewById(R.id.FD_passwordRestCon_d);

        prefs = view.getContext().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        databaseReference = FirebaseDatabase.getInstance().getReference("doctor");
        //============================Defines=======================================================
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("إنتظر قليلاً يتم تحديث البيانات..");
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        update_btn.setOnClickListener(v -> uploadData());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Phone_number = snapshot.child(restoredText).child("personal_info").child("Phone").getValue(String.class);
                String email = snapshot.child(restoredText).child("personal_info").child("Email").getValue(String.class);
                String address = snapshot.child(restoredText).child("doctor_info").child("المحافظة").getValue(String.class);

                Phone_number_t.setText(Phone_number);
                email_t.setText(email);
                address_t.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void uploadData() {
        progressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String phone = Phone_number_t.getText().toString();
                    String email = email_t.getText().toString();
                    String adders = address_t.getText().toString();

                    databaseReference.child(restoredText).child("personal_info").child("Phone").setValue(phone);
                    databaseReference.child(restoredText).child("personal_info").child("Email").setValue(email);
                    databaseReference.child(restoredText).child("doctor_info").child("المحافظة").setValue(adders);
                    RestPassword();
                    progressDialog.dismiss();
                    getData();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void RestPassword() {
        String passwordS = password.getText().toString();
        String coPasswordS = coPassword.getText().toString();

        if (((Basic_Activity) requireActivity()).validPassword(passwordS)) {
            Toast.makeText(getContext(), "كلمة المرور يجب أن تكون أكثر من 8 أرقام", Toast.LENGTH_SHORT).show();
        } else if (((Basic_Activity) requireActivity()).validCoPassword(passwordS, coPasswordS)) {
            Toast.makeText(getContext(), "كلمة المرور غير متاطبقة", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.child(restoredText).child("Password").setValue(passwordS);
        }
    }
}