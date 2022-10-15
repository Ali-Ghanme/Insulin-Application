package com.example.diabestes_care_app.chat;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.Doctor_Chat_Adapter;
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

public class Chat_D extends Basic_Activity {

    ImageView backButton, SendButton;
    CircleImageView profile_image;
    TextView name, status;
    private DatabaseReference myRef;
    EditText chatEditText;
    RecyclerView ChatRecyclerView;
    private final List<ChatList_Model> chatListModels = new ArrayList<>();
    private Doctor_Chat_Adapter chatAdapter;
    private boolean loadingFirstTime = true;
    DatabaseReference online_status_all_users;
    String snooping_status, restoredText, chatKey, getUsername, getName, getProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_d);

        //============================Defines=======================================================
        backButton = findViewById(R.id.chat_back_btn_d);
        SendButton = findViewById(R.id.chat_send_message_d);
        profile_image = findViewById(R.id.chat_profile_pic_d);
        name = findViewById(R.id.chat_user_name_d);
        chatEditText = findViewById(R.id.chat_message_box_EditText_d);
        ChatRecyclerView = findViewById(R.id.Chat_RecyclerView_d);
        status = findViewById(R.id.chat_user_status_d);

        //============================Defines SharedPreferences=====================================
        SharedPreferences prefs = Chat_D.this.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //============================Defines RecycleView ==========================================
        ChatRecyclerView.setHasFixedSize(true);
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(Chat_D.this));

        //============================Defines Adapter===============================================
        chatAdapter = new Doctor_Chat_Adapter(chatListModels, Chat_D.this);
        ChatRecyclerView.setAdapter(chatAdapter);

        //============================Get data from message adapter class===========================
        getUsername = getIntent().getStringExtra("username_d");
        getName = getIntent().getStringExtra("name_d");
        getProfilePic = getIntent().getStringExtra("profile_pic_d");
        chatKey = getIntent().getStringExtra("chat_key_d");

        myRef = FirebaseDatabase.getInstance().getReference().child(restoredText);
        online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(getUsername);

        //============================= Image Profile + name =======================================
        name.setText(getName);
        Glide.with(this).load(getProfilePic).into(profile_image);

        //======================== Send,Store,Show Message =========================================
        SendMessage();
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
                    }
                }
                if (snapshot.hasChild("Chat")) {

                    if (snapshot.child("Chat").child(chatKey).hasChild("messages")) {

                        chatListModels.clear();

                        for (DataSnapshot messagesSnapshot : snapshot.child("Chat").child(chatKey).child("messages").getChildren()) {

                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("username")) {

                                String messageTimestamps = messagesSnapshot.getKey();

                                final String getUsername = messagesSnapshot.child("username").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);

                                Calendar cal = Calendar.getInstance(Locale.getDefault());
                                assert messageTimestamps != null;
                                cal.setTimeInMillis(Long.parseLong(messageTimestamps) * 1000);
                                String date22 = DateFormat.format("dd-MM-yyyy", cal).toString();
                                String time = DateFormat.format(" hh:mm:aa", cal).toString();

                                ChatList_Model chatListModel = new ChatList_Model(getUsername, getName, getMsg, date22, time);
                                chatListModels.add(chatListModel);

                                String data = MemoryData.getLastMsgTS(Chat_D.this, chatKey);
                                if (data.isEmpty()) {
                                    data = "0";
                                }

                                if (messageTimestamps.isEmpty()) {
                                    messageTimestamps = "0";
                                }
                                if (loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(data)) {
                                    loadingFirstTime = false;

                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat_D.this);

                                    chatAdapter.updateChatList(chatListModels);

                                    ChatRecyclerView.scrollToPosition(chatListModels.size() + 1);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    void SendMessage() {
        SendButton.setOnClickListener(v -> {
            final String geTextMessage = chatEditText.getText().toString();
            // get current timesTamps
            final String currentTimestamps = String.valueOf(System.currentTimeMillis()).substring(0, 10);

            myRef.child("Chat").child(chatKey).child("patient_1").setValue(getUsername);
            myRef.child("Chat").child(chatKey).child("doctor_2").setValue(restoredText);
            myRef.child("Chat").child(chatKey).child("messages").child(currentTimestamps).child("msg").setValue(geTextMessage);
            myRef.child("Chat").child(chatKey).child("messages").child(currentTimestamps).child("username").setValue(restoredText);
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
}