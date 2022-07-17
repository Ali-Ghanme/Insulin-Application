package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

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
import com.example.diabestes_care_app.Adapters.Doctor_Messages_Adapter;
import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.Models.MessagesList_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class Chat_Fragment_D extends Fragment {
    // Variables
    private RecyclerView messagesRecycleReview;
    // Variables
    String DoctorUsername;
    // Variables
    DatabaseReference myRef;
    // Variables
    CircleImageView imageView;
    // Variables
    ArrayList<MessagesList_Model> messagesListModels = new ArrayList<>();
    // Adapter
    Doctor_Messages_Adapter messagesAdapter;
    // Variables
    private int unseenMessage = 0;
    private String lastMessage = "";
    private String chatKey = "";
    private boolean dataSet = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat___d, container, false);

        //============================Defines=======================================================
        messagesRecycleReview = view.findViewById(R.id.FCH_Chat_RecyclerView_d);
        imageView = view.findViewById(R.id.FCH_profile_img_d);

        //============================Defines Firebase==============================================
        myRef = FirebaseDatabase.getInstance().getReference();
        GetDataFromFirebase();

        //============================Recycle Review Setup==========================================
        messagesRecycleReview.setHasFixedSize(true);
        messagesRecycleReview.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesAdapter = new Doctor_Messages_Adapter(messagesListModels, getContext());
        messagesRecycleReview.setAdapter(messagesAdapter);

        //============================Defines SharedPreferences=====================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        Toast.makeText(getContext(), DoctorUsername, Toast.LENGTH_SHORT).show();
        return view;
    }

    //============================Get profile Image Profile from Firebase database==================
    @Override
    public void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String PatientImage = snapshot.child(DoctorUsername).child("personal_info").child("Image").child("mImageUrI").getValue(String.class);
                Glide.with(getContext()).load(PatientImage).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //========================Get Patient list Data From Firebase Function===========================
    private void GetDataFromFirebase() {
        Query query = myRef.child("patient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagesListModels.clear();
                unseenMessage = 0;
                lastMessage = "";
                chatKey = "";
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final String getPatientName = snapshot.child("personal_info").child("name").getValue(String.class);
                        final String getPatientUsername = snapshot.child("username").getValue(String.class);
                        dataSet = false;
                        final String getPatientImage = snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);


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

                                            if ((getUserOne.equals(DoctorUsername) && getUserTow.equals(getPatientUsername)) || (getUserOne.equals(getPatientUsername) && getUserTow.equals(DoctorUsername))) {
                                                for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {

                                                    final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                                    final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(getContext(),chatKey));

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
                                    MessagesList_Model messagesListModel = new MessagesList_Model(getPatientName, getPatientUsername,
                                            lastMessage, getPatientImage, chatKey, unseenMessage);
                                    messagesListModels.add(messagesListModel);
                                    messagesRecycleReview.setAdapter(new Doctor_Messages_Adapter(messagesListModels, getContext()));
                                    messagesAdapter.UpdateData(messagesListModels);
                                    messagesAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "إنتظر قليلاً ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }
}