package com.example.diabestes_care_app.Sing_up_pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.diabestes_care_app.R;

public class Sing_Up_3 extends AppCompatActivity {
    CheckBox checkbox_one, checkbox_tow, checkbox_thre, checkbox_one1, checkbox_tow2, checkbox_thre3;
    Button btn_test, btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up3);


        checkbox_one = findViewById(R.id.checkbox_one);
        checkbox_tow = findViewById(R.id.checkbox_tow);
        checkbox_thre = findViewById(R.id.checkbox_thre);
        checkbox_one1 = findViewById(R.id.checkbox_one1);
        checkbox_tow2 = findViewById(R.id.checkbox_tow2);
        checkbox_thre3 = findViewById(R.id.checkbox_thre3);
        btn_test = findViewById(R.id.btn_test);
        btn_next = findViewById(R.id.btn_next_3);
        //====================================Define Checkbox===============================

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int totalamount = 0;
                StringBuilder result = new StringBuilder();
                result.append("Selected Items:");
                if (checkbox_one.isChecked()) {
                    result.append("\nالنوع الاول ");
                    totalamount += 100;
                }
                if (checkbox_tow.isChecked()) {
                    result.append("\n النوع الثاني ");
                    totalamount += 50;
                }
                if (checkbox_thre.isChecked()) {
                    result.append("\nسكري الحمل  ");
                    totalamount += 120;
                }
                if (checkbox_one1.isChecked()) {
                    result.append("\nBurger ابر انسلوين ");
                    totalamount += 120;
                }
                if (checkbox_tow2.isChecked()) {
                    result.append("\n حبوب ");
                    totalamount += 120;
                }
                if (checkbox_thre3.isChecked()) {
                    result.append("\nالاثنين معا  ");
                    totalamount += 120;
                }
                result.append("\nTotal: " + totalamount + "Rs");
                //Displaying the message on the toast
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();

            }
        });

        //====================================End Define Checkbox===============================


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