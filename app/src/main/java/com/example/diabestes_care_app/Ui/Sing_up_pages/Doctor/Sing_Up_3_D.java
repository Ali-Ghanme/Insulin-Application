package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Sing_Up_3_D extends Basic_Activity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    Button Button_Next;
    EditText auto_UnName, auto_certificate, auto_Grad_Country;
    EditText mDate;
    final Calendar myCalendar = Calendar.getInstance();
    String[] university = {" اخرى ", "  الازهر", " فلسطين ", "الاسلامية", "الإسراء"};
    String[] city = {"الضفة الغربية ", "جنين  ", "نابلس  ", "قطاع غزة"};
    String[] certificate = {" دكتورا ", "ماجستير  ", "بكالوريس  ", " دبلوم"};
    String UnameS;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_3_d);
        //====================================Define variables===============================
        Button_Next = findViewById(R.id.Sp3_bt321_next_D);
        auto_UnName = findViewById(R.id.Sp3_UnName_D);
        auto_certificate = findViewById(R.id.Sp3_certificate_D);
        auto_Grad_Country = findViewById(R.id.Sp3_Grad_Country_D);
        mDate = findViewById(R.id.Sp3_et_year_Grade_D);

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username2");
        //====================================Spinner===============================


        auto_UnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_3_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_3_D.this, R.layout.activity_listview, university);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        UnameS = listView.getAdapter().getItem(position).toString();
                        auto_UnName.setText(UnameS);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });


        //====================================Spinner===============================

        auto_UnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_3_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_3_D.this, R.layout.activity_listview, university);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String UCertificate = listView.getAdapter().getItem(position).toString();
                        auto_certificate.setText(UCertificate);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //====================================Spinner===============================

        auto_UnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_3_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_3_D.this, R.layout.activity_listview, university);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String Grade_Country = listView.getAdapter().getItem(position).toString();
                        auto_Grad_Country.setText(Grade_Country);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //====================================Spinner===============================
        Button_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DoctorUname = auto_certificate.getText().toString();
                String DoctorCertificate = auto_certificate.getText().toString();
                String DoctorGradeCountry = auto_Grad_Country.getText().toString();


                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("اسم الجامعة").setValue(DoctorUname);
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("بلد التخرج").setValue(DoctorCertificate);
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("الشهادة الجامعية").setValue(DoctorGradeCountry);
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
                String DoctorMData = mDate.getText().toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("تاريخ التخرج").setValue(DoctorMData);
            }
        });
    }
}