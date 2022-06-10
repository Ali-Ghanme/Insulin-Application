package com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_Fragmane;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class Personal_Info extends Fragment {

    DatabaseReference myRef;
    String restoredText;
    TextView weight_t, length_t, age_t, date_t, Phone_number_t, email_t, address_t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal__info, container, false);

        weight_t = view.findViewById(R.id.FD_weight);
        length_t = view.findViewById(R.id.FD_length);
        date_t = view.findViewById(R.id.FD_date);
        Phone_number_t = view.findViewById(R.id.FD_Phone_number);
        email_t = view.findViewById(R.id.FD_email);
        age_t = view.findViewById(R.id.FD_age);
        address_t = view.findViewById(R.id.FD_address);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        myRef.addValueEventListener(new ValueEventListener() {
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
//                age_t.setText(getAge());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private String getAge(int year, int month, int day , String data ){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
}