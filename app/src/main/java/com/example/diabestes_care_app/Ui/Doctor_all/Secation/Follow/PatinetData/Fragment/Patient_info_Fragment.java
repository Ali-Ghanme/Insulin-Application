package com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Adapters.Doctor_Follow_Adapter.MyPREFERENCES_P_Username_D;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Patient_info_Fragment extends Fragment {
    ImageView patient_profile;
    TextView name, type, age, city, gender, tall, weight;
    TextView medicsType, another, injury_date, injury_factor;
    DatabaseReference databaseReference;
    String getName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patinet_info, container, false);

        //=========================================Define===========================================
        patient_profile = view.findViewById(R.id.FPI_img_Profile);
        name = view.findViewById(R.id.FPI_name);
        type = view.findViewById(R.id.FPI_type);
        age = view.findViewById(R.id.FPI_age);
        city = view.findViewById(R.id.FPI_city);
        gender = view.findViewById(R.id.FPI_gender);
        tall = view.findViewById(R.id.FPI_tall);
        weight = view.findViewById(R.id.FPI_weight);
        medicsType = view.findViewById(R.id.FPI_Medics_Type);
        another = view.findViewById(R.id.FPI_another);
        injury_date = view.findViewById(R.id.FPI_injury_date);
        injury_factor = view.findViewById(R.id.FPI_injury_factor);

        // Get data from message adapter class
        SharedPreferences prefs = getActivity().getSharedPreferences(MyPREFERENCES_P_Username_D, MODE_PRIVATE);
        getName = prefs.getString("PatientUsername_D", null);

        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(getName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                String image = snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue().toString();
                Glide.with(getActivity()).load(image).into(patient_profile);

                name.setText(snapshot.child("personal_info").child("name").getValue().toString());
                age.setText(snapshot.child("personal_info").child("Age").getValue().toString());
                city.setText(snapshot.child("personal_info").child("City").getValue().toString());
                gender.setText(snapshot.child("personal_info").child("gender").getValue().toString());
                tall.setText(snapshot.child("personal_info").child("tall").getValue().toString());
                weight.setText(snapshot.child("personal_info").child("wehigt").getValue().toString());

                type.setText(snapshot.child("disease_info").child("Diabetes Type").getValue().toString());
                medicsType.setText(snapshot.child("disease_info").child("Diabetes Medics Type").getValue().toString());
                another.setText(snapshot.child("disease_info").child("أمراض أخرى").getValue().toString());
                injury_date.setText(snapshot.child("disease_info").child("تاريخ الاصابة").getValue().toString());
                injury_factor.setText(snapshot.child("disease_info").child("عوامل الاصابة").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}