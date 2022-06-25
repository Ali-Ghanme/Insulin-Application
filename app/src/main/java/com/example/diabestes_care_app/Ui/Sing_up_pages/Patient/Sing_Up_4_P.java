package com.example.diabestes_care_app.Ui.Sing_up_pages.Patient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Sing_Up_4_P extends Basic_Activity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    Button btn_next;
    EditText mDateInjury, mCause, others;
    final Calendar myCalendar = Calendar.getInstance();
    CheckBox C_Other;
    ListView listView;
    String[] cause = {"تاريخ عائلي", "التعرض لأمراض فيروسية", "العمر", "الوزن",};
    String[] other_ill = {"أمراض الجهاز التنفسي", "ضغط الدم", "أمراض القلب"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_p);

        //====================================Define Checkbox===============================
        btn_next = findViewById(R.id.Sp4_bt_next_P);
        mDateInjury = findViewById(R.id.Sp4_et_enjerdDate_P);
        mCause = findViewById(R.id.Sp4_et_cause_P);
        others = findViewById(R.id.SP4_et_others_P);
        C_Other = findViewById(R.id.Sp4_diabetis_others_P);

        mCause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_4_P.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_P.this, R.layout.activity_listview, cause);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String Cause = listView.getAdapter().getItem(position).toString();
                        mCause.setText(Cause);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_4_P.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_P.this, R.layout.activity_listview, other_ill);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String other_ill = listView.getAdapter().getItem(position).toString();
                        others.setText(other_ill);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        C_Other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                others.setFocusable(isChecked);
            }
        });

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username3");
        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Sing_Up_4_P.this, "Hallow", Toast.LENGTH_SHORT).show();

                // get data form edit text into string variables
                String patientDateInjury = mDateInjury.getText().toString();
                String patientCauses = mCause.getText().toString();
                String patientOthers = others.getText().toString();
                String patientCheck = C_Other.getText().toString();

                databaseReference.child("patient").child(patient_userName).child("disease_info").child("تاريخ الاصابة").setValue(patientDateInjury);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("عوامل الاصابة").setValue(patientCauses);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("أمراض أخرى").setValue(patientOthers);
                databaseReference.child("patient").child(patient_userName).child("disease_info").child("أمراض أخرى").setValue(patientCheck);
                Toast.makeText(Sing_Up_4_P.this, "User have registered successfully ", Toast.LENGTH_SHORT).show();
                finish();

                Intent intent4 = new Intent(Sing_Up_4_P.this, Sing_Up_5_P.class);
                intent4.putExtra("username4", patient_userName);
                startActivity(intent4);
            }
        });


        //====================================DataPicker===============================
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        mDateInjury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Sing_Up_4_P.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.UK);
        mDateInjury.setText(dateFormat.format(myCalendar.getTime()));
    }
}