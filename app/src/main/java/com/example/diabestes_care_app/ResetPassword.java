package com.example.diabestes_care_app;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {
    Dialog dialog;
    Button request;
    EditText et_email, et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Create + Configure the Dialog here
        dialog = new Dialog(ResetPassword.this);
        dialog.setContentView(R.layout.chech_email_and_phone);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button oky = dialog.findViewById(R.id.okey);
        et_email = dialog.findViewById(R.id.et_email);
        et_phone = dialog.findViewById(R.id.et_phone);
//        chatKey = "";
        //============================Show Consu Dialog and send request for doctor=================
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(); // Showing the dialog here
            }
        });
        oky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResetPassword.this, "Success Process", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
