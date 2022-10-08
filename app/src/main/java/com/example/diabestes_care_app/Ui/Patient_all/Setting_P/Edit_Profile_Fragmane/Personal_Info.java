package com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

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

import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Personal_Info extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");

    String restoredText;
    EditText Phone_number_t, email_t, address_t;
    Button update_btn;
    SharedPreferences prefs;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__info, container, false);
        //============================Defines=======================================================
        Phone_number_t = view.findViewById(R.id.FD_Phone_number);
        email_t = view.findViewById(R.id.FD_email);
        address_t = view.findViewById(R.id.FD_address);
        update_btn = view.findViewById(R.id.Update_btn);

        prefs = view.getContext().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        databaseReference = FirebaseDatabase.getInstance().getReference("patient");
        //============================Defines=======================================================
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("إنتظر قليلاً يتم تحديث البيانات..");
        progressDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            databaseReference.child(restoredText).child("personal_info").child("City").setValue(adders);
                            Toast.makeText(getContext(), "Data is Updated", Toast.LENGTH_SHORT).show();
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
        });
        return view;
    }
    // Hallow this is Update
    @Override
    public void onStart() {
        super.onStart();
        getData();
    }
    void getData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Phone_number = snapshot.child(restoredText).child("personal_info").child("Phone").getValue(String.class);
                String email = snapshot.child(restoredText).child("personal_info").child("Email").getValue(String.class);
                String address = snapshot.child(restoredText).child("personal_info").child("City").getValue(String.class);

                Phone_number_t.setText(Phone_number);
                email_t.setText(email);
                address_t.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}