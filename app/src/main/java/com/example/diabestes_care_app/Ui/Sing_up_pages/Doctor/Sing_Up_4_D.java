package com.example.diabestes_care_app.Ui.Sing_up_pages.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sing_Up_4_D extends Basic_Activity {

    DatabaseReference databaseReference;
    EditText auto_City, auto_SubCity, auto_Qu, auto_WP;
    Button ButtonNext;
    ListView listView;
    String[] city = {"الضفة الغربية ", "قطاع غزة"};
    String[] SubCity = {"الشمال", "دير البلح", "خانيونس", "رفح", "غزة"};
    String[] Qu = {"نعم", "لا"};
    String[] WP = {"خاص", "حكومي"};
    String doctor_userName;

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
        doctor_userName = intentUsername.getStringExtra("username3");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("doctor");

        //============================Spinner Function==============================================
        auto_City.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_4_D.this).inflate(R.layout.layout_bottom_sheet_main, findViewById(R.id.bottomSheetContier));

            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, city);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String City = listView.getAdapter().getItem(position).toString();
                auto_City.setText(City);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //============================Spinner Function==============================================
        auto_SubCity.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_4_D.this).inflate(R.layout.layout_bottom_sheet_main, findViewById(R.id.bottomSheetContier));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, SubCity);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String SubCity = listView.getAdapter().getItem(position).toString();
                auto_SubCity.setText(SubCity);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //============================Spinner Function==============================================
        auto_Qu.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_4_D.this).inflate(R.layout.layout_bottom_sheet_main, findViewById(R.id.bottomSheetContier));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, Qu);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String Qu = listView.getAdapter().getItem(position).toString();
                auto_Qu.setText(Qu);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //============================Spinner Function==============================================
        auto_WP.setOnClickListener(v -> {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    Sing_Up_4_D.this, R.style.BottomSheetDialogTheme);
            View bottomSheetView = LayoutInflater.from(Sing_Up_4_D.this).inflate(R.layout.layout_bottom_sheet_main, findViewById(R.id.bottomSheetContier));
            listView = bottomSheetView.findViewById(R.id.City_bottom_listView);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(Sing_Up_4_D.this, R.layout.activity_listview, WP);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String WP_S = listView.getAdapter().getItem(position).toString();
                auto_WP.setText(WP_S);
                bottomSheetDialog.dismiss();
            });

            bottomSheetView.findViewById(R.id.City_bottom_listView);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();
        });

        //============================Button Click==============================================
        ButtonNext.setOnClickListener(v -> {
            String DoctorCity = auto_City.getText().toString();
            String DoctorSubCity = auto_SubCity.getText().toString();
            String DoctorQu = auto_Qu.getText().toString();
            String DoctorWp = auto_WP.getText().toString();

            databaseReference.child(doctor_userName).child("doctor_info").child("المدينة").setValue(DoctorCity);
            databaseReference.child(doctor_userName).child("doctor_info").child("المحافظة").setValue(DoctorSubCity);
            databaseReference.child(doctor_userName).child("doctor_info").child("العيادة الخارجية").setValue(DoctorQu);
            databaseReference.child(doctor_userName).child("doctor_info").child("طبيعية العمل").setValue(DoctorWp);
            Intent intent4 = new Intent(Sing_Up_4_D.this, Sing_up_5_D.class);
            intent4.putExtra("username4", doctor_userName);
            startActivity(intent4);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        exitHappen(this, databaseReference, doctor_userName);
    }

}