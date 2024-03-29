package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Sing_Up_3_D extends Basic_Activity {

    DatabaseReference databaseReference;
    Button Button_Next;
    EditText UnName, Certificate, Grad_Country, mDate;
    final Calendar myCalendar = Calendar.getInstance();
    String[] university = {"الأقصى", "  الازهر", " فلسطين ", "الاسلامية", "الإسراء"};
    String[] city = {"المملكة المتحدة", "الولايات المتحدة", "الأردن", "فلسطين"};
    String[] certificate = {" دكتورا ", "ماجستير  ", "بكالوريس  ", " دبلوم"};
    String DoctorMData, DoctorUname, DoctorCertificate, DoctorGradeCountry;
    ListView listView;
    String doctor_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_3_d);
        //====================================Define variables===============================
        Button_Next = findViewById(R.id.Sp3_bt321_next_D);
        UnName = findViewById(R.id.Sp3_UnName_D);
        Certificate = findViewById(R.id.Sp3_certificate_D);
        Grad_Country = findViewById(R.id.Sp3_Grad_Country_D);
        mDate = findViewById(R.id.Sp3_et_year_Grade_D);

        Intent intentUsername = getIntent();
        doctor_userName = intentUsername.getStringExtra("username2");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor");

        //====================================Spinner===============================
        UnName.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_3_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_3_D.this).inflate(R.layout.layout_bottom_sheet_main,
                    findViewById(R.id.bottomSheetContier_main));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_3_D.this, R.layout.activity_listview, university);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String UnameS = listView.getAdapter().getItem(position).toString();
                UnName.setText(UnameS);
                bottomSheetDialog.dismiss();
            });
            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //====================================Spinner===============================

        Certificate.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_3_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_3_D.this).inflate(R.layout.layout_bottom_sheet_main,
                    findViewById(R.id.bottomSheetContier));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_3_D.this, R.layout.activity_listview, certificate);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String UCertificate = listView.getAdapter().getItem(position).toString();
                Certificate.setText(UCertificate);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //====================================Spinner===============================
        Grad_Country.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_3_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_3_D.this).inflate(R.layout.layout_bottom_sheet_main,
                    findViewById(R.id.bottomSheetContier));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_3_D.this, R.layout.activity_listview, city);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String UGrade_Country = listView.getAdapter().getItem(position).toString();
                Grad_Country.setText(UGrade_Country);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //====================================Spinner===============================
        Button_Next.setOnClickListener(v -> {
            DoctorUname = UnName.getText().toString();
            DoctorCertificate = Certificate.getText().toString();
            DoctorGradeCountry = Grad_Country.getText().toString();
            if (validIsEmpty(DoctorUname, DoctorCertificate, DoctorGradeCountry, DoctorUname, DoctorCertificate, DoctorGradeCountry)) {
                Toast.makeText(Sing_Up_3_D.this, "إملأ جميع الحقول", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child(doctor_userName).child("doctor_info").child("اسم الجامعة").setValue(DoctorUname);
                databaseReference.child(doctor_userName).child("doctor_info").child("بلد التخرج").setValue(DoctorCertificate);
                databaseReference.child(doctor_userName).child("doctor_info").child("الشهادة الجامعية").setValue(DoctorGradeCountry);
                Intent intent3 = new Intent(Sing_Up_3_D.this, Sing_Up_4_D.class);
                intent3.putExtra("username3", doctor_userName);
                startActivity(intent3);
                finish();
            }

        });

        //====================================DataPicker===============================
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            updateLabel(mDate, myCalendar, "dd/MM/yyyy");
        };
        mDate.setOnClickListener(view -> {
            new DatePickerDialog(Sing_Up_3_D.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            DoctorMData = mDate.getText().toString();
            if (DoctorMData.isEmpty()) {
                Toast.makeText(Sing_Up_3_D.this, "إملأ جميع الحقول", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("doctor").child(doctor_userName).child("doctor_info").child("تاريخ التخرج").setValue(DoctorMData);
            }
        });
    }

    public void onBackPressed() {
        exitHappen(this, databaseReference, doctor_userName);
    }
}