package com.example.diabestes_care_app.chat;

import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView name;
    private final DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://diabeticsproject-default-rtdb.firebaseio.com/");
    EditText chatEditText;
    String GenerateChatKey = "";
    String restoredText;
    String chatKey;
    RecyclerView ChatRecyclerView;
    private final List<ChatList_Model> chatListModels = new ArrayList<>();
    private Doctor_Chat_Adapter chatAdapter;
    private boolean loadingFirstTime = true;

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

        //============================Defines SharedPreferences=====================================
        SharedPreferences prefs = Chat_D.this.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        //============================Defines SharedPreferences=====================================
        ChatRecyclerView.setHasFixedSize(true);
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(Chat_D.this));

        //============================Defines SharedPreferences=====================================
        chatAdapter = new Doctor_Chat_Adapter(chatListModels, Chat_D.this);
        ChatRecyclerView.setAdapter(chatAdapter);

        //============================Get data from message adapter class===========================
        String getName = getIntent().getStringExtra("name");
        String getProfilePic = getIntent().getStringExtra("profile_pic");
        String getUsername = getIntent().getStringExtra("username");
        Log.e("TAG", getUsername);

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
                        chatKey = String.valueOf(snapshot.child("chat").getChildrenCount());
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

                                Calendar cal = Calendar.getInstance(Locale.getDefault());
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

                                    ChatRecyclerView.scrollToPosition(chatListModels.size() + 100);
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