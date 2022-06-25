package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_4_D extends Basic_Activity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText auto_City, auto_SubCity, auto_Qu, auto_WP;
    Button ButtonNext;
    ListView listView;
    String[] city = {"الضفة الغربية ", "قطاع غزة"};
    String[] SubCity = {"الشمال", "دير البلح", "خانيونس", "رفح", "غزة"};
    String[] Qu = {"نعم", "لا"};
    String[] WP = {"خاص", "حكومي"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_sign_up_4_d);

        //============================Spinner Function==============================================
        ButtonNext = findViewById(R.id.Sp4_bt_next_D);
        auto_City = findViewById(R.id.Sp4_City_D);
        auto_SubCity = findViewById(R.id.Sp4_subCity_D);
        auto_Qu = findViewById(R.id.Sp4_have_work_D);
        auto_WP = findViewById(R.id.Sp4_placeOfWork_D);

        Intent intentUsername = getIntent();
        String patient_userName = intentUsername.getStringExtra("username3");

        //============================Spinner Function==============================================


        auto_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, city);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String City = listView.getAdapter().getItem(position).toString();
                        auto_City.setText(City);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //============================Spinner Function==============================================


        auto_SubCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, SubCity);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String SubCity = listView.getAdapter().getItem(position).toString();
                        auto_SubCity.setText(SubCity);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        //============================Spinner Function==============================================

        auto_Qu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, Qu);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String Qu = listView.getAdapter().getItem(position).toString();
                        auto_Qu.setText(Qu);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //============================Spinner Function==============================================

        auto_WP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContier)
                        );
                listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, WP);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        String WP_S = listView.getAdapter().getItem(position).toString();
                        auto_WP.setText(WP_S);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.City_bottom_listView);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });
        //============================Button Click==============================================
        ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DoctorCity = auto_City.getText().toString();
                String DoctorSubCity = auto_SubCity.getText().toString();
                String DoctorQu = auto_Qu.getText().toString();
                String DoctorWp = auto_WP.getText().toString();

                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("المدينة").setValue(DoctorCity);
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("المحافظة").setValue(DoctorSubCity);
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("العيادة الخارجية").setValue(DoctorQu);
                databaseReference.child("doctor").child(patient_userName).child("doctor_info").child("طبيعية العمل").setValue(DoctorWp);
                Intent intent4 = new Intent(Sing_Up_4_D.this, Sing_up_5_D.class);
                intent4.putExtra("username4", patient_userName);
                startActivity(intent4);
                finish();
            }
        });
    }
}