package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Home_Doctor;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_2_P;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_4_P;
import com.example.diabestes_care_app.Ui.Sing_up_pages.Patient.Sing_Up_5_P;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_4_D extends Basic_Activity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    AutoCompleteTextView auto_City, auto_SubCity, auto_Qu, auto_WP;
    Button ButtonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_d);

        //============================Spinner Function==============================================
        ButtonNext = findViewById(R.id.Sp4_bt_next_D);
        auto_City = findViewById(R.id.Sp4_City_auto_D);
        auto_SubCity = findViewById(R.id.Sp4_subCity_auto_D);
        auto_Qu = findViewById(R.id.Sp4_have_work_auto_D);
        auto_WP = findViewById(R.id.Sp4_placeOfWork_auto_D);

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username3");

        //============================Spinner Function==============================================
        String[] city = {"الضفة الغربية ", "قطاع غزة"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.spinner_list_item, city);
        auto_City.setAdapter(itemAdapter);
        auto_City.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String doctorCity = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("المدينة").setValue(doctorCity);
                Toast.makeText(Sing_Up_4_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
        //============================Spinner Function==============================================
        String[] SubCity = {"الشمال", "دير البلح", "خانيونس", "رفح", "غزة"};
        ArrayAdapter<String> itemAdapter2 = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.spinner_list_item, SubCity);
        auto_SubCity.setAdapter(itemAdapter2);
        auto_SubCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String SubCity = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("المحافظة").setValue(SubCity);
                Toast.makeText(Sing_Up_4_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
        //============================Spinner Function==============================================
        String[] Qu = {"نعم", "لا"};
        ArrayAdapter<String> itemAdapter3 = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.spinner_list_item, Qu);
        auto_Qu.setAdapter(itemAdapter3);
        auto_Qu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String doctorQu = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("عيادة خارجية").setValue(doctorQu);
                Toast.makeText(Sing_Up_4_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
        //============================Spinner Function==============================================
        String[] WP = {"خاص", "حكومي"};
        ArrayAdapter<String> itemAdapter4 = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.spinner_list_item, WP);
        auto_WP.setAdapter(itemAdapter4);
        auto_WP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String doctorWP = parent.getItemAtPosition(position).toString();
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("طبيعة العمل").setValue(doctorWP);
                Toast.makeText(Sing_Up_4_D.this, "Data Add Successfully ", Toast.LENGTH_SHORT).show();
            }
        });
        //============================Button Click==============================================
        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Sing_Up_4_D.this, Sing_up_5_D.class);
                intent4.putExtra("username4", patient_userName);
                startActivity(intent4);
                finish();
            }
        });
    }
}