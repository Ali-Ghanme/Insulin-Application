package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_3_P extends Basic_Activity {
    Button btn_next;
    RadioGroup RG_mType;
    RadioButton mTypeOption;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    String data1, data2, data3, data4, data5, data6, data7;
    String strType;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    Dialog dialog;
    Button close, continues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_3_p);
        //====================================Define Checkbox=======================================
        btn_next = findViewById(R.id.Sp3_bt_next_P);
        RG_mType = findViewById(R.id.Sp3_RG_type_P);

        checkBox1 = findViewById(R.id.Sp3_medics_types_1_P);
        checkBox2 = findViewById(R.id.Sp3_medics_types_2_P);
        checkBox3 = findViewById(R.id.Sp3_medics_types_3_P);
        checkBox4 = findViewById(R.id.Sp3_medics_types_4_P);
        checkBox5 = findViewById(R.id.Sp3_medics_types_5_P);
        checkBox6 = findViewById(R.id.Sp3_medics_types_6_P);
        checkBox7 = findViewById(R.id.Sp3_medics_types_7_P);


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data1 = checkBox1.getText().toString();
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data2 = checkBox2.getText().toString();
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data3 = checkBox3.getText().toString();
            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data4 = checkBox4.getText().toString();
            }
        });
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data5 = checkBox5.getText().toString();
            }
        });
        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data6 = checkBox6.getText().toString();
            }
        });
        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                data7 = checkBox7.getText().toString();
            }
        });

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


        //=================Gender Radio Group to get Data And set into Firebase=====================
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cheek if user fill all data fields before sending data to firebase
                if (validIsEmpty(strType, strType, strType, strType, strType, strType)) {
                    Toast.makeText(Sing_Up_3_P.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("patient").child(patient_userName).child("disease_info").child("Diabetes Type").setValue(strType);
                    databaseReference.child("patient").child(patient_userName).child("disease_info").child("Diabetes Medics Type").
                            setValue(data1 + "," + data2 + "," + data3 + "," + data4 + "," + data5 + "," + data6 + "," + data7);
                    Toast.makeText(Sing_Up_3_P.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
                    finish();

                    Intent intent3 = new Intent(Sing_Up_3_P.this, Sing_Up_4_P.class);
                    intent3.putExtra("username3", patient_userName);
                    startActivity(intent3);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(Sing_Up_3_P.this);
        dialog.setContentView(R.layout.exite_layout);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
        close = dialog.findViewById(R.id.Close);
        continues = dialog.findViewById(R.id.Continue2);
        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
