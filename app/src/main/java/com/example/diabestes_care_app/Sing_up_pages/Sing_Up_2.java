package com.example.diabestes_care_app.Sing_up_pages;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.android.material.textfield.TextInputLayout;


public class Sing_Up_2 extends Basic_Activity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView auto_1;
    Button btn_next;
    EditText password, confirm_Password;
    boolean password_is_visible;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up2);

        //============================Defines==================================
        textInputLayout = findViewById(R.id.S2_city_menu_drop);
        auto_1 = (AutoCompleteTextView) findViewById(R.id.Sign_up_2_autoComplete_textview_1);
        btn_next = findViewById(R.id.btn_next_2);
        password = findViewById(R.id.S2_et_Password);
        confirm_Password = findViewById(R.id.S2_et_confirm_Password);

        //============================Spinner Function==================================
        String[] country = {"الضفة الغربية ", "جنين  ", "نابلس  ", "قطاع غزة"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(Sing_Up_2.this, R.layout.spinner_list_item, country);
        auto_1.setAdapter(itemAdapter);
        auto_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//          Toast.makeText((String)parent.getItemAtPosition(position)); set textview specific text
            }
        });

        //============================Show password and hid password==================================
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (password_is_visible) {
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_hide, 0);
                            //for hide password
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password_is_visible = false;
                        } else {
                            //set drawable image here
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_show, 0);
                            //for hide password
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            password_is_visible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        //============================Show password and hid password==================================
        confirm_Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= confirm_Password.getRight() - confirm_Password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = confirm_Password.getSelectionEnd();
                        if (password_is_visible) {
                            //set drawable image here
                            confirm_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_hide, 0);
                            //for hide password
                            confirm_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            password_is_visible = false;
                        } else {
                            //set drawable image here
                            confirm_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_password_show, 0);
                            //for hide password
                            confirm_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            password_is_visible = true;
                        }
                        confirm_Password.setSelection(selection);
                        return true;
                    }
                }
                return false;
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