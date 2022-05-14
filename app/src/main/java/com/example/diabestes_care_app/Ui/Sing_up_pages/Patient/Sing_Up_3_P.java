package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor.Sing_Up_4_D;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_3_P extends Basic_Activity {
    Button btn_next;
    RadioGroup RG_mType, Rg_mMedics;
    RadioButton mTypeOption, mMedics;
    String strType, strMedics;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_3_p);
        //====================================Define Checkbox=======================================
        btn_next = findViewById(R.id.Sp3_bt_next_P);
        RG_mType = findViewById(R.id.Sp3_RG_type_P);
        Rg_mMedics = findViewById(R.id.Sp3_medics_types_P);

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username2");
        //===================Gender Radio Group to get Data And set into Firebase===================
        RG_mType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mTypeOption = RG_mType.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.Sp3_diabetis_type_1_P:
                        strType = mTypeOption.getText().toString();
                        break;
                    case R.id.Sp3_diabetis_type_2_P:
                        strType = mTypeOption.getText().toString();
                        break;
                    case R.id.Sp3_diabetis_type_3_P:
                        strType = mTypeOption.getText().toString();
                        break;
                    default:
                }
            }
        });

        Rg_mMedics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mMedics = Rg_mMedics.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.Sp3_medics_types_1_P:
                        strMedics = mMedics.getText().toString();
                        break;
                    case R.id.Sp3_medics_types_2_P:
                        strMedics = mMedics.getText().toString();
                        break;
                    case R.id.Sp3_medics_types_3_P:
                        strMedics = mMedics.getText().toString();
                        break;
                    default:
                }
            }
        });

        //=================Gender Radio Group to get Data And set into Firebase=====================
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cheek if user fill all data fields before sending data to firebase
                if (validIsEmpty(strMedics, strType, strMedics, strType, strMedics, strType)) {
                    Toast.makeText(Sing_Up_3_P.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("patient").child(patient_userName).child("disease_info").child("Diabetes Type").setValue(strType);
                    databaseReference.child("patient").child(patient_userName).child("disease_info").child("Diabetes Medics Type").setValue(strMedics);
                    Toast.makeText(Sing_Up_3_P.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
                    finish();

                    Intent intent3 = new Intent(Sing_Up_3_P.this, Sing_Up_4_P.class);
                    intent3.putExtra("username3", patient_userName);
                    startActivity(intent3);

                }
            }
        });
    }
}
