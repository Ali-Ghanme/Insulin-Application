package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_2_P;

public class Sing_Up_4_D extends Basic_Activity {
    Button btn_next_S;
    AutoCompleteTextView auto_1 ,auto_2,auto_3,auto_4 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_d);
        auto_1 = (AutoCompleteTextView) findViewById(R.id.Sign_up_3_auto_certificate);
        auto_2 = (AutoCompleteTextView) findViewById(R.id.Sign_up_3_auto_city);
        auto_3 = (AutoCompleteTextView) findViewById(R.id.Sign_up_3_auto_year);
        auto_4 = (AutoCompleteTextView) findViewById(R.id.Sign_up_3_auto_university);
        btn_next_S = findViewById(R.id.btn_Upload);
        //====================================Define variables===============================
        btn_next_S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sing_Up_4_D.this, Home_Doctor.class);
                startActivity(intent);
            }
        });
//######################### Spinner #################################
        String[] certificate = {" دكتورا ", "ماجستير  ", "بكالوريس  ", " دبلوم"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, certificate);
        auto_1.setAdapter(itemAdapter);
        auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText((String)parent.getItemAtPosition(position)); set textview specific text
            }
        });
//######################### Spinner #################################
        String[] city = {"الضفة الغربية ", "جنين  ", "نابلس  ", "قطاع غزة"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, city);
        auto_1.setAdapter(adapter);
        auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText((String)parent.getItemAtPosition(position)); set textview specific text
            }
        });
        //######################### Spinner #################################
        String[] year = {" 2003 ", "  2002", " 2001 ", " 2000"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, year);
        auto_1.setAdapter(adapter1);
        auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText((String)parent.getItemAtPosition(position)); set textview specific text
            }
        });
        //######################### Spinner #################################
        String[] university = {" اخرى ", "  الازهر", " فلسطين ", " الاسلامية"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, university);
        auto_1.setAdapter(adapter2);
        auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText((String)parent.getItemAtPosition(position)); set textview specific text
            }
        });
    }
}