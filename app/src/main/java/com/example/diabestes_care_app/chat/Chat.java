package com.example.diabestes_care_app.chat;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.Patient_Chat_Adapter;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.Models.ChatList_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends Basic_Activity {

    ImageView backButton, SendButton;
    CircleImageView profile_image;
    TextView name, status;
    private DatabaseReference myRef, online_status_all_users;
    EditText chatEditText;
    RecyclerView ChatRecyclerView;
    List<ChatList_Model> chatListModels = new ArrayList<>();
    private Patient_Chat_Adapter chatAdapter;
    private boolean loadingFirstTime = true;
    String chatKey, restoredText, getUsername, getName, getProfilePic, snooping_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //============================Defines=======================================================
        backButton = findViewById(R.id.chat_back_btn);
        SendButton = findViewById(R.id.chat_send_message);
        profile_image = findViewById(R.id.chat_profile_pic);
        name = findViewById(R.id.chat_user_name);
        chatEditText = findViewById(R.id.chat_message_box_EditText);
        ChatRecyclerView = findViewById(R.id.Chat_RecyclerView);
        status = findViewById(R.id.chat_user_status);

        //============================Defines SharedPreferences=====================================
        SharedPreferences prefs = Chat.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //============================Defines Recyclerview Configuration============================
        ChatRecyclerView.setHasFixedSize(true);
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        //============================Defines Adapter Configuration=================================
        chatAdapter = new Patient_Chat_Adapter(chatListModels, Chat.this);
        ChatRecyclerView.setAdapter(chatAdapter);

        //============================Get data from message adapter class===========================
        getUsername = getIntent().getStringExtra("username");
        getName = getIntent().getStringExtra("name");
        getProfilePic = getIntent().getStringExtra("profile_pic");
        chatKey = getIntent().getStringExtra("chat_key");

        online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(getUsername);
        myRef = FirebaseDatabase.getInstance().getReference().child(getUsername);

        // getUsername is the doctor username
        Log.e("TAG","This is the patient username "+ getUsername);

        name.setText(getName);
        Glide.with(this).load(getProfilePic).into(profile_image);

        //======================== Send,Store,Show Message =========================================
        SendMassages();
        ShowMassage();
        onlineStatus();
        backButton.setOnClickListener(v -> onBackPressed());
    }


    void ShowMassage() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Generate chat key by default key is 1
                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("Chat")) {
                        chatKey = String.valueOf(snapshot.child("Chat").getChildrenCount() + 1);
                        Log.e("TAG", chatKey);
                    }
                }
                if (snapshot.hasChild("Chat")) {

                    if (snapshot.child("Chat").child(chatKey).hasChild("messages")) {

                        chatListModels.clear();

                        for (DataSnapshot messagesSnapshot : snapshot.child("Chat").child(chatKey).child("messages").getChildren()) {

                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("username")) {

                                String messageTimestamps = messagesSnapshot.getKey();

                                 String getUsername = messagesSnapshot.child("username").getValue(String.class);
                                 String getMsg = messagesSnapshot.child("msg").getValue(String.class);

                                Calendar cal = Calendar.getInstance(Locale.getDefault());
                                assert messageTimestamps != null;
                                cal.setTimeInMillis(Long.parseLong(messageTimestamps) * 1000);
                                String date22 = DateFormat.format("dd-MM-yyyy", cal).toString();
                                String time = DateFormat.format(" hh:mm:aa", cal).toString();

                                ChatList_Model chatListModel = new ChatList_Model(getUsername, getName, getMsg, date22, time);
                                chatListModels.add(chatListModel);

                                String datda = MemoryData.getLastMsgTS(Chat.this, chatKey);
                                if (datda.isEmpty()) {
                                    datda = "0";
                                }

                                if (messageTimestamps.isEmpty()) {
                                    messageTimestamps = "0";
                                }
                                if (loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(datda)) {
                                    loadingFirstTime = false;

                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat.this);

                                    chatAdapter.updateChatList(chatListModels);

                                    ChatRecyclerView.scrollToPosition(chatListModels.size());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void SendMassages() {
        SendButton.setOnClickListener(v -> {
            final String geTextMessage = chatEditText.getText().toString();
            // get current timesTamps
            final String currentTimestamps = String.valueOf(System.currentTimeMillis()).substring(0,10);

            myRef.child("Chat").child(chatKey).child("patient_1").setValue(restoredText);
            myRef.child("Chat").child(chatKey).child("doctor_2").setValue(getUsername);
            myRef.child("Chat").child(chatKey).child("messages").child(currentTimestamps).child("msg").setValue(geTextMessage);
            myRef.child("Chat").child(chatKey).child("messages").child(currentTimestamps).child("username").setValue(restoredText);
//                notification(geTextMessage);
            chatEditText.clearAnimation();
            chatEditText.getText().clear();
        });

    }

    void onlineStatus() {
        //    ============================Online/Offline read Status ===================================
        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snooping_status = dataSnapshot.getValue(String.class);
                if (snooping_status == null) {
                    Log.e("TAG", "ni Status for this use");
                } else {
                    try {
                        if (snooping_status.contentEquals("online")) {
                            status.setText("نشط");
                        } else {
                            status.setText("غير نشط");
                            status.setTextColor(Color.parseColor("#D3D3D3"));
                        }
                    } catch (Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", databaseError.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myRef.child("Chat").child(chatKey).removeValue();
    }

    private void notification(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")
                .setContentTitle("Message From" + restoredText)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentText(message);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(999, builder.build());
    }
}