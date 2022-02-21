package com.example.diabestes_care_app.Sing_up_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diabestes_care_app.R;




public class Sing_Up_2 extends AppCompatActivity  {
    String[] country = {"الضفة الغربية ","جنين  ","نابلس  ", "قطاع غزة"};
    String[] Governorate = {"جنوب غزة ","غرب غزة ","شرق غزة  ", "شمال غزة "};
    AutoCompleteTextView auto_1;
    AutoCompleteTextView auto_2;
    ArrayAdapter<String> adapterItems;
    Button btn_next ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up2);
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        auto_1 = (AutoCompleteTextView)findViewById(R.id.autoComplete1);
        auto_2 = (AutoCompleteTextView)findViewById(R.id.autoComplete2);
        btn_next = findViewById(R.id.btn_next);


        ArrayAdapter a2 =new ArrayAdapter(this, android.R.layout.simple_spinner_item,country);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auto_1.setAdapter(a2);
        auto_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter a1 =new ArrayAdapter(this, android.R.layout.simple_spinner_item,Governorate);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        auto_2.setAdapter(a1);

        auto_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Sing_Up_3.class);
                startActivity(intent);
            }

        });
    }

}