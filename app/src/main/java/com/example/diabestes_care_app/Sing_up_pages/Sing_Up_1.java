package com.example.diabestes_care_app.Sing_up_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diabestes_care_app.R;

public class Sing_Up_1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // create array of Strings
    String[] type_people = {"أنثى","ذكر"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up1);
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //====================================Define variables===============================
        Spinner spin = (Spinner) findViewById(R.id.spinner_type);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter Spinner_Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type_people);
        Spinner_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(Spinner_Adapter);  //Setting the ArrayAdapter data on the Spinner
    }
    //====================================Spinner Method=====================================
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), type_people[position], Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "لا يمكن ترك هذا الخيار فارغاً", Toast.LENGTH_SHORT).show();
    }
}