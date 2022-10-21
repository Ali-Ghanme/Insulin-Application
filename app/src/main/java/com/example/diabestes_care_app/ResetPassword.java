package com.example.diabestes_care_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ResetPassword extends Basic_Activity {

    EditText et_userEmail, et_userName;
    Button send_email;
    String FEmail, FPin;
    // Firebase
    DatabaseReference databaseReference;
    String EEmail, EUserName;
    ImageView imageView;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        et_userEmail = findViewById(R.id.et_email);
        et_userName = findViewById(R.id.et_username);
        send_email = findViewById(R.id.send_email);
        imageView = findViewById(R.id.imageView4);

        imageView.setOnClickListener(v -> onBackPressed());
        send_email.setOnClickListener(v -> {
            EEmail = et_userEmail.getText().toString();
            EUserName = et_userName.getText().toString();

            databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(EUserName).child("personal_info");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        FPin = (dataSnapshot.child("PIN").getValue()).toString();
                        FEmail = (dataSnapshot.child("Email").getValue()).toString();
                        Log.e("TAG", FEmail);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "هناك شيء ما خاطئ", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", e.getMessage());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            if (et_userEmail.getText().toString().equals(FEmail)) {
                SendMailFin(EEmail, FPin);
                Log.e("TAG", ":" + FEmail);
                Intent intent = new Intent(ResetPassword.this, RestPassowrd2.class);
                intent.putExtra("UsernamePassword", EUserName);
                startActivity(intent);

            } else {
                Toast.makeText(this, "تأكد من البيانات المدخلة ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void SendMailFin(String To, String Subject) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("gly703105@gmail.com", "ljvvpdislqmjavgl");
                }
            });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("gly703105@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(et_userEmail.getText().toString()));
                message.setSubject("مرحبا بك لقد طلبت طلب إعادة تعيين كلمة السر الخاصة بك \n هذا كود اعادة ظبط لكمة السر الرجاء عدم مشاركة الكود");
                message.setText("كود اعادة تعيين كلمة السر الخاصة بك هو :\n" + Subject);
                message.setHeader("Hallow", "One Tow");
                Transport.send(message);
            } catch (Exception e) {
                Log.e("TAG", "First " + e.getMessage());
            }

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
            Log.e("TAG", "Second" + e.getMessage());
        }

    }

}
