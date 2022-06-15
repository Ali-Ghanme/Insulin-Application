package com.example.diabestes_care_app.NotificationSender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diabestes_care_app.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class SendNotification extends AppCompatActivity {
    private EditText et_title, et_subject;
    private Button notification_general, notification_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
// for sending notification to all - Mohammed Siam
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        et_title = findViewById(R.id.et_title);
        et_subject = findViewById(R.id.et_subject);
        notification_general = findViewById(R.id.notification_general);


        notification_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_title.getText().toString().isEmpty() && !et_subject.getText().toString().isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all", et_title.getText().toString(),
                            et_subject.getText().toString(), getApplicationContext(), SendNotification.this);
                    notificationsSender.SendNotifications();
                } else {
                    Toast.makeText(getApplicationContext(), "Hi , text && Title required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}