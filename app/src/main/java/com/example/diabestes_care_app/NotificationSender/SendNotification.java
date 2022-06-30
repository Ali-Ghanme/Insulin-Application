package com.example.diabestes_care_app.NotificationSender;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diabestes_care_app.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class SendNotification extends AppCompatActivity {
    private EditText et_title, et_subject;
    private Button notification_general, notification_user;
    String token = "TOKENcmFFwB6TRJavKcwD5WdNsh:APA91bHtTDNxIstASOgGw_chSuAmYZ-WUvZPn53cer6ClUkgg7lxc-e6nsJmJndQY7-Nsnl2dKYLWXxtQDRktFy1LT8A4mmdL7G430fh125E3tx5pTrzC8ZbjlEXmF45N-PW3r0rTsBs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

// for sending notification to all - Mohammed Siam
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        et_title = findViewById(R.id.et_title);
        et_subject = findViewById(R.id.et_subject);

        notification_general = findViewById(R.id.notification_general_1);
        notification_user = findViewById(R.id.notification_user_1);


        notification_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_title = et_title.getText().toString();
                String t_subject = et_subject.getText().toString();

                if (!t_title.isEmpty() && !t_subject.isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all", et_title.getText().toString(),
                            et_subject.getText().toString(), getApplicationContext(), SendNotification.this);
                    notificationsSender.SendNotifications();

                } else {
                    Toast.makeText(getApplicationContext(), "Hi , text && Title required", Toast.LENGTH_LONG).show();
                }
            }
        });
        notification_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_title = et_title.getText().toString();
                String t_subject = et_subject.getText().toString();
                if (!t_title.isEmpty() && !t_subject.isEmpty() && !token.isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(token, t_title, t_subject, getApplicationContext(), SendNotification.this);
                    notificationsSender.SendNotifications();
                } else {
                    Toast.makeText(SendNotification.this, "Enter Token", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}