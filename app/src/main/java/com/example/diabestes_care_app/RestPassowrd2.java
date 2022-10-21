package com.example.diabestes_care_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestPassowrd2 extends Basic_Activity {
    DatabaseReference databaseReference;
    EditText Pin;
    Button button;
    String Username, uPIN, fPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_passowrd2);
        button = findViewById(R.id.Next_btn);
        Pin = findViewById(R.id.et_PIN);
        uPIN = Pin.getText().toString();
        Username = getIntent().getStringExtra("UsernamePassword");

        button.setOnClickListener(v -> {
            databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(Username).child("personal_info");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    fPIN = snapshot.child("PIN").getValue().toString();
                    Log.e("TAG","This from firebase: "+ fPIN + "This is from user " + Pin.getText().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            if (!validCoPassword(Pin.getText().toString(), fPIN)) {
                Toast.makeText(this, "PIN خاطئ", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(RestPassowrd2.this, RestPassword3.class);
                intent.putExtra("UsernamePassword", Username);
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