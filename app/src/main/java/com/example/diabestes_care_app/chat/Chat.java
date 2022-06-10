package com.example.diabestes_care_app.chat;

import static com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P.Home_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Base_Activity.Basic_Activity;
import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.chat.Adapter.ChatAdapter;
import com.example.diabestes_care_app.chat.Model.ChatList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private final List<ChatList> chatLists = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private boolean loadingFirstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullscreen();
        setContentView(R.layout.activity_chat);

        backButton = findViewById(R.id.chat_back_btn);
        SendButton = findViewById(R.id.chat_send_message);
        profile_image = findViewById(R.id.chat_profile_pic);
        name = findViewById(R.id.chat_user_name);
        chatEditText = findViewById(R.id.chat_message_box_EditText);
        ChatRecyclerView = findViewById(R.id.Chat_RecyclerView);

        SharedPreferences prefs = Chat.this.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);

        ChatRecyclerView.setHasFixedSize(true);
        ChatRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter = new ChatAdapter(chatLists, Chat.this);
        ChatRecyclerView.setAdapter(chatAdapter);

        // Get data from message adapter class
        String getName = getIntent().getStringExtra("name");
        String getProfilePic = getIntent().getStringExtra("profile_pic");
        String getUsername = getIntent().getStringExtra("username");
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
                        chatLists.clear();

                        for (DataSnapshot messagesSnapshot : snapshot.child("chat").child(chatKey).child("messages").getChildren()) {

                            if (messagesSnapshot.hasChild("msg") && messagesSnapshot.hasChild("username")) {

                                final String messageTimestamps = messagesSnapshot.getKey();
                                final String getUsername = messagesSnapshot.child("username").getValue(String.class);
                                final String getMsg = messagesSnapshot.child("msg").getValue(String.class);


                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy" , Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:aa" , Locale.getDefault());

                                ChatList chatList = new ChatList(getUsername,getName,getMsg,simpleDateFormat.format(date),simpleTimeFormat.format(date));
                                chatLists.add(chatList);

                                if (loadingFirstTime || Long.parseLong(messageTimestamps) > Long.parseLong(MemoryData.getLastMsgTS(Chat.this, chatKey))) {

                                    loadingFirstTime = false;

                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey, Chat.this);

                                    chatAdapter.updateChatList(chatLists);

                                    ChatRecyclerView.scrollToPosition(chatLists.size() - 1 );
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