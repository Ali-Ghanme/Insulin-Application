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
import com.google.android.material.textfield.TextInputLayout;


public class Sing_Up_2 extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView auto_1, auto_2;
    Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up2);
        // code  is to make the Activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textInputLayout = findViewById(R.id.Sing_up_2_city_menu_drop);
        auto_1 = (AutoCompleteTextView) findViewById(R.id.Sign_up_2_autoComplete_textview_1);
        btn_next = findViewById(R.id.btn_next_2);

        String[] country = {"الضفة الغربية ", "جنين  ", "نابلس  ", "قطاع غزة"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(Sing_Up_2.this, R.layout.spinner_list_item, country);
        auto_1.setAdapter(itemAdapter);
        auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText((String)parent.getItemAtPosition(position)); set textview specific text
            }
        });
        //====================================الانتقال من صفحة الستجيل الحالية للصفحة الثانية ===============================
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Sing_Up_3.class);
                startActivity(intent);
            }
        });
    }
}