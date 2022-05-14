package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_3_P;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_4_P;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Sing_Up_3_D extends Basic_Activity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    Button Button_Next;
    AutoCompleteTextView auto_UnName, auto_certificate, auto_Grad_Country;
    EditText mDate;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_3_d);
        //====================================Define variables===============================
        Button_Next = findViewById(R.id.Sp3_bt321_next_D);
        auto_UnName = findViewById(R.id.Sp3_UnName_auto_D);
        auto_certificate = findViewById(R.id.Sp3_certificate_auto_D);
        auto_Grad_Country = findViewById(R.id.Sp3_Grad_Country_auto_D);
        mDate = findViewById(R.id.Sp3_et_year_Grade_D);

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username2");
        //====================================Spinner===============================
        String[] university = {" اخرى ", "  الازهر", " فلسطين ", " الاسلامية"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, university);
        auto_UnName.setAdapter(itemAdapter);
        auto_UnName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Sing_Up_3_D.this, (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String doctorUN = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("اسم الجامعة").setValue(doctorUN);
                Toast.makeText(Sing_Up_3_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
        //====================================Spinner===============================

        String[] city = {"الضفة الغربية ", "جنين  ", "نابلس  ", "قطاع غزة"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, city);
        auto_Grad_Country.setAdapter(adapter);
        auto_Grad_Country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String doctorUN = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("بلد التخرج").setValue(doctorUN);
                Toast.makeText(Sing_Up_3_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
        //====================================Spinner===============================
        String[] certificate = {" دكتورا ", "ماجستير  ", "بكالوريس  ", " دبلوم"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_list_item, certificate);
        auto_certificate.setAdapter(adapter2);
        auto_certificate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String doctorUN = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("الشهادة الجامعية").setValue(doctorUN);
                Toast.makeText(Sing_Up_3_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();

            }
        });

        //====================================Spinner===============================
        Button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Sing_Up_3_D.this, Sing_Up_4_D.class);
                intent3.putExtra("username3", patient_userName);
                startActivity(intent3);
                finish();
            }
        });
        //====================================DataPicker===============================
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                updateLabel(mDate, myCalendar, "dd/MM/yyyy");
            }
        };
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Sing_Up_3_D.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}