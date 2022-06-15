package com.example.diabestes_care_app.chat;


import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.Chat_Adapter;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.Models.ChatList_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends Basic_Activity {
    ImageView backButton, SendButton;
    CircleImageView profile_image;
    TextView name;
    private final DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText chatEditText;
    String GenerateChatKey = "";
    String restoredText;
    String chatKey;
    RecyclerView ChatRecyclerView;
    private final List<ChatList_Model> chatListModels = new ArrayList<>();
    private Chat_Adapter chatAdapter;
    private boolean loadingFirstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_chat);

        //============================Defines=======================================================
        backButton = findViewById(R.id.chat_back_btn);
        SendButton = findViewById(R.id.chat_send_message);
        profile_image = findViewById(R.id.chat_profile_pic);
        name = findViewById(R.id.chat_user_name);
        chatEditText = findViewById(R.id.chat_message_box_EditText);
        ChatRecyclerView = findViewById(R.id.Chat_RecyclerView);

        //============================Defines SharedPreferences=====================================
        SharedPreferences prefs = Chat.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //
        Toast.makeText(this, restoredText, Toast.LENGTH_SHORT).show();
        //============================Defines SharedPreferences=====================================
        ChatRecyclerView.setHasFixedSize(true);
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        //============================Defines SharedPreferences=====================================
        chatAdapter = new Chat_Adapter(chatListModels, Chat.this);
        ChatRecyclerView.setAdapter(chatAdapter);

        //============================Get data from message adapter class===========================
        String getName = getIntent().getStringExtra("name");
        String getProfilePic = getIntent().getStringExtra("profile_pic");
        String getUsername = getIntent().getStringExtra("username");

        // getUsername is the patient username the account that i loge in by it
        Toast.makeText(this, getUsername, Toast.LENGTH_SHORT).show();

        chatKey = getIntent().getStringExtra("chat_key");

        name.setText(getName);
        Glide.with(this).load(getProfilePic).into(profile_image);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Generate chat key by default key is 1
                if (chatKey.isEmpty()) {
                    chatKey = "1";
                    if (snapshot.hasChild("chat")) {
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }

                if (snapshot.hasChild("chat")) {

                    if (snapshot.child("chat").child(chatKey).hasChild("messages")) {
                        chatListModels.clear();

                        for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {

                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("username")) {

                                String messageTimestamps = messagesSnapshot.getKey();
                                final String getUsername = messagesSnapshot.child("username").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);


                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));

                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:aa", Locale.getDefault());

                                Calendar cal = Calendar.getInstance(Locale.getDefault());
                                cal.setTimeInMillis(Long.parseLong(messageTimestamps) * 1000);
                                String date22 = DateFormat.format("dd-MM-yyyy", cal).toString();
                                String timeee = DateFormat.format(" hh:mm:aa", cal).toString();

                                ChatList_Model chatListModel = new ChatList_Model(getUsername, getName, getMsg, date22, timeee);
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

                                    ChatRecyclerView.scrollToPosition(chatListModels.size() - 1);
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

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String geTextMessage = chatEditText.getText().toString();
                // get current timesTamps
                final String currentTimestamps = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                myRef.child("chat").child(chatKey).child("patient_1").setValue(restoredText);
                myRef.child("chat").child(chatKey).child("doctor_2").setValue(getUsername);
                myRef.child("chat").child(chatKey).child("messages").child(currentTimestamps).child("msg").setValue(geTextMessage);
                myRef.child("chat").child(chatKey).child("messages").child(currentTimestamps).child("username").setValue(restoredText);
                chatEditText.clearAnimation();
                chatEditText.getText().clear();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}