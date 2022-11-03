package com.example.diabestes_care_app.Ui.Sing_In.RestPassword_D;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestPassword3_d extends Basic_Activity {

    DatabaseReference databaseReference;
    String Username;
    EditText editText1, editText2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_password3);

        editText1 = findViewById(R.id.et_password1);
        editText2 = findViewById(R.id.et_password2);
        button = findViewById(R.id.save_pass);

        Username = getIntent().getStringExtra("UsernamePassword");

        databaseReference = FirebaseDatabase.getInstance().getReference("doctor").child(Username);

        button.setOnClickListener(v -> {
            if (validIsEmpty(editText1.getText().toString(), editText2.getText().toString(), editText1.getText().toString(),
                    editText2.getText().toString(), editText1.getText().toString(), editText2.getText().toString()) || validPassword(editText1.getText().toString()) ||
                    validCoPassword(editText1.getText().toString(), editText2.getText().toString())) {
                Toast.makeText(RestPassword3_d.this, "كلمة السر لا تلبي متطلبات الأمان", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Password").setValue(editText1.getText().toString());
                databaseReference.child("CoPassword").setValue(editText2.getText().toString());
                Intent intent = new Intent(RestPassword3_d.this, RestPassword4_d.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}