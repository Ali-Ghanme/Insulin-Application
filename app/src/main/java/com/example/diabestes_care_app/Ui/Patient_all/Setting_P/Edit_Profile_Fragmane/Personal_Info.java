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
    EditText weight_t, length_t, age_t, date_t, Phone_number_t, email_t, address_t;
    Button update_btn;
    SharedPreferences prefs;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__info, container, false);
        //============================Defines=======================================================
        weight_t = view.findViewById(R.id.FD_weight);
        length_t = view.findViewById(R.id.FD_length);
        date_t = view.findViewById(R.id.FD_date);
        Phone_number_t = view.findViewById(R.id.FD_Phone_number);
        email_t = view.findViewById(R.id.FD_email);
        age_t = view.findViewById(R.id.FD_age);
        address_t = view.findViewById(R.id.FD_address);
        update_btn = view.findViewById(R.id.Update_btn);
//                        databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);
//                        databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);
//                        databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);
//                        databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);
//                        databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);
//                        databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);

        String tall = length_t.getText().toString();
        String date = date_t.getText().toString();
        String phone = Phone_number_t.getText().toString();
        String email = email_t.getText().toString();
        String age = age_t.getText().toString();
        String adders = address_t.getText().toString();


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
//                progressDialog.show();
                String wehigt = weight_t.getText().toString();


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            databaseReference.child(restoredText).child("personal_info").child("wehigt").setValue(wehigt);

                            Toast.makeText(getContext(), "Data is Updated", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("TAG",e.getMessage());
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

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String weight = snapshot.child(restoredText).child("personal_info").child("wehigt").getValue(String.class);
                String length = snapshot.child(restoredText).child("personal_info").child("tall").getValue(String.class);
                String date = snapshot.child(restoredText).child("personal_info").child("Date").getValue(String.class);
                String Phone_number = snapshot.child(restoredText).child("personal_info").child("Phone").getValue(String.class);
                String email = snapshot.child(restoredText).child("personal_info").child("Email").getValue(String.class);
                String address = snapshot.child(restoredText).child("personal_info").child("City").getValue(String.class);

                weight_t.setText(weight);
                length_t.setText(length);
                date_t.setText(date);
                Phone_number_t.setText(Phone_number);
                email_t.setText(email);
                address_t.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

//    private void updateUser(String name, String email) {
//        // updating the user via child nodes
//        if (!TextUtils.isEmpty(name))
//            databaseReference.child(restoredText).child("name").setValue(name);
//
//        if (!TextUtils.isEmpty(email))
//            databaseReference.child(restoredText).child("email").setValue(email);
//    }

    // Hallow world this is update
}