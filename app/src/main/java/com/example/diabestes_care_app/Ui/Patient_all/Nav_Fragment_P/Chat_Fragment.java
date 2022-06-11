package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.MessagesAdapter;
import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.Models.MessagesList;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Chat_Fragment extends Fragment {
    private RecyclerView messagesRecycleReview;
    String restoredText;
    DatabaseReference myRef;
    CircleImageView imageView;
    // Variables
    ArrayList<MessagesList> messagesLists = new ArrayList<>();
    // Adapter
    MessagesAdapter messagesAdapter;
    private int unseenMessage = 0;
    private String lastMessage = "";
    private String chatKey = "";
    private boolean dataSet = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        //============================Defines=======================================================
        messagesRecycleReview = view.findViewById(R.id.FCH_Chat_RecyclerView);
        imageView = view.findViewById(R.id.FCH_profile_img_p);
        myRef = FirebaseDatabase.getInstance().getReference();
        GetDataFromFirebase();
        messagesRecycleReview.setHasFixedSize(true);
        messagesRecycleReview.setLayoutManager(new LinearLayoutManager(getContext()));

        messagesAdapter = new MessagesAdapter(messagesLists, getContext());
        messagesRecycleReview.setAdapter(messagesAdapter);
        //============================Defines=======================================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        restoredText = prefs.getString("TAG_NAME", null);
        return view;
    }

    //============================Get profile Image Profile from Firebase database=========================================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("patient");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String PatientImage = snapshot.child(restoredText).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                Glide.with(getContext()).load(PatientImage).into(imageView);

                // Save Username to MemoryData
                MemoryData.saveData(restoredText, getContext());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //========================Get Doctor list Data From Firebase Function===========================
    private void GetDataFromFirebase() {
        Query query = myRef.child("doctor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagesLists.clear();
                unseenMessage = 0;
                lastMessage = "";
                chatKey = "";
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final String getName = snapshot.child("personal_info").child("name_ar").getValue().toString();
                        final String getUsername = snapshot.child("personal_info").child("username").getValue().toString();
                        dataSet = false;
                        final String getDoctorImage = snapshot.child("personal_info").child("Image").child("mImageUrI").getValue().toString();


                        myRef.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts = (int) snapshot.getChildrenCount();

                                if (getChatCounts > 0) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;

                                        if (dataSnapshot1.hasChild("patient_1") && dataSnapshot1.hasChild("doctor_2") && dataSnapshot1.hasChild("messages")) {
                                            final String getUserOne = dataSnapshot1.child("patient_1").getValue(String.class);
                                            final String getUserTow = dataSnapshot1.child("doctor_2").getValue(String.class);

                                            if ((getUserOne.equals(getUsername) && getUserTow.equals(restoredText)) || (getUserOne.equals(restoredText) && getUserTow.equals(getUsername))) {
                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {
                                                    final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(getContext(), getKey));
                                                    lastMessage = chatDataSnapshot.child("msg").getValue(String.class);
                                                    if (getMessageKey > getLastSeenMessage) {
                                                        unseenMessage++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!dataSet) {
                                    dataSet = true;
                                    MessagesList messagesList = new MessagesList(getName, getUsername, lastMessage, getDoctorImage, chatKey, unseenMessage);
                                    messagesLists.add(messagesList);
                                    messagesAdapter.UpdateData(messagesLists);
                                    messagesAdapter.notifyDataSetChanged();
                                    messagesRecycleReview.setAdapter(new MessagesAdapter(messagesLists, getContext()));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "waite second catch error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
    // Fix All Gradle Problem
}