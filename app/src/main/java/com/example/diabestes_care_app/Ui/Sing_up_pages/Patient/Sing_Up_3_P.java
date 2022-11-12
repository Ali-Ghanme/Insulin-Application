package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_3_P extends Basic_Activity {
    Button btn_next, btn_next2;
    RadioGroup RG_mType;
    RadioButton mTypeOption;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    String data1, data2, data3, data4, data5, data6, data7;
    String strType;
    DatabaseReference databaseReference;
    String patient_userName;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_3_p);
        //====================================Define Checkbox=======================================
        btn_next = findViewById(R.id.Sp3_bt_next_P);
        btn_next2 = findViewById(R.id.Sp3_bt_next_P2);
        RG_mType = findViewById(R.id.Sp3_RG_type_P);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("patient");
        Intent intentUsername = getIntent();
        patient_userName = intentUsername.getStringExtra("username2");

        checkBox1 = findViewById(R.id.Sp3_medics_types_1_P);
        checkBox2 = findViewById(R.id.Sp3_medics_types_2_P);
        checkBox3 = findViewById(R.id.Sp3_medics_types_3_P);
        checkBox4 = findViewById(R.id.Sp3_medics_types_4_P);
        checkBox5 = findViewById(R.id.Sp3_medics_types_5_P);
        checkBox6 = findViewById(R.id.Sp3_medics_types_6_P);
        checkBox7 = findViewById(R.id.Sp3_medics_types_7_P);

        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> data1 = checkBox1.getText().toString());
        checkBox2.setOnCheckedChangeListener((buttonView, isChecked) -> data2 = checkBox2.getText().toString());
        checkBox3.setOnCheckedChangeListener((buttonView, isChecked) -> data3 = checkBox3.getText().toString());
        checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> data4 = checkBox4.getText().toString());
        checkBox5.setOnCheckedChangeListener((buttonView, isChecked) -> data5 = checkBox5.getText().toString());
        checkBox6.setOnCheckedChangeListener((buttonView, isChecked) -> data6 = checkBox6.getText().toString());
        checkBox7.setOnCheckedChangeListener((buttonView, isChecked) -> data7 = checkBox7.getText().toString());

        //===================Gender Radio Group to get Data And set into Firebase===================
        RG_mType.setOnCheckedChangeListener((group, checkedId) -> {
            mTypeOption = RG_mType.findViewById(checkedId);
            switch (checkedId) {
                case R.id.Sp3_diabetis_type_1_P:
                case R.id.Sp3_diabetis_type_2_P:
                case R.id.Sp3_diabetis_type_3_P:
                    strType = mTypeOption.getText().toString();
                    break;
                default:
            }
        });

        //=================Gender Radio Group to get Data And set into Firebase=====================
        btn_next.setOnClickListener(view -> saveData());

        //=================Next Button get Data And save into Firebase==============================
        btn_next2.setOnClickListener(view -> saveData());
    }

    @Override
    public void onBackPressed() {
        exitHappen(this, databaseReference, patient_userName);
    }
    void saveData() {
        // cheek if user fill all data fields before sending data to firebase
        if (validIsEmpty(strType, strType, strType, strType, strType, strType)) {
            Toast.makeText(Sing_Up_3_P.this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.child(patient_userName).child("disease_info").child("Diabetes Type").setValue(strType);
            databaseReference.child(patient_userName).child("disease_info").child("Diabetes Medics Type").
                    setValue(data1 + "," + data2 + "," + data3 + "," + data4 + "," + data5 + "," + data6 + "," + data7);
            finish();

            Intent intent3 = new Intent(Sing_Up_3_P.this, Sing_Up_4_P.class);
            intent3.putExtra("username3", patient_userName);
            startActivity(intent3);
        }
    }

}
