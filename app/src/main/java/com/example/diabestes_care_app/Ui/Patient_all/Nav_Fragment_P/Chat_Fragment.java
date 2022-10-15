package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
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
import com.example.diabestes_care_app.Adapters.Doctor_Follow_Adapter;
import com.example.diabestes_care_app.Adapters.Patient_Messages_Adapter;
import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.Models.Follow_Model;
import com.example.diabestes_care_app.Models.MessagesList_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class Chat_Fragment extends Fragment {
    // RecyclerView
    private RecyclerView messagesRecycleReview;
    // Shared Preference
    SharedPreferences prefs;
    // Patient Username from shared Preference
    String PatientUsername;
    // Firebase
    DatabaseReference myRef2, myRef;
    // Image View Patient Profile
    CircleImageView imageView_Profile;
    // ArrayList
    ArrayList<MessagesList_Model> messagesListModels = new ArrayList<>();
    // Adapter
    Patient_Messages_Adapter messagesAdapter;
    // Variables
    private int unseenMessage = 0;
    private String lastMessage = "";
    private String chatKey = "";
    boolean dataSet = false;

    List<Follow_Model> list;
    // Adapter
    Doctor_Follow_Adapter doctor_follow_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        //============================Defines=======================================================
        messagesRecycleReview = view.findViewById(R.id.FCH_Chat_RecyclerView);
        imageView_Profile = view.findViewById(R.id.FCH_profile_img_p);

        //============================Defines=======================================================
        myRef2 = FirebaseDatabase.getInstance().getReference("patient");
        myRef = FirebaseDatabase.getInstance().getReference();
        GetFollowingPatient();
        //============================Defines=======================================================
        prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        Toast.makeText(getContext(), PatientUsername, Toast.LENGTH_SHORT).show();

        //============================Setup Recycle Review==========================================
        messagesRecycleReview.setHasFixedSize(true);
        messagesRecycleReview.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesAdapter = new Patient_Messages_Adapter(messagesListModels, getContext());
        messagesRecycleReview.setAdapter(messagesAdapter);

        return view;
    }

    //============================Get profile Image Profile from Firebase database==================
    @Override
    public void onStart() {
        super.onStart();
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                String PatientImage = snapshot.child(PatientUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                Glide.with(requireContext()).load(PatientImage).into(imageView_Profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //======================================Function================================================
    //Get Following from Doctor account
    private void GetFollowingPatient() {
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                messagesListModels.clear();
                unseenMessage = 0;
                lastMessage = "";
                chatKey = "";

                Follow_Model follow_model;
                for (DataSnapshot sn : snapshot.child(PatientUsername).child("Following By").getChildren()) {
                    follow_model = new Follow_Model();
                    follow_model.setUsername(sn.getKey());
                    Log.e("TAG", "This is the chat key " + follow_model.getUsername());


                    Follow_Model finalFollow_model = follow_model;
                    myRef.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
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

                                        assert getUserOne != null;
                                        if ((getUserOne.equals(finalFollow_model.getUsername()) && Objects.equals(getUserTow, PatientUsername)) || (getUserOne.equals(PatientUsername)
                                                && Objects.equals(getUserTow, finalFollow_model.getUsername()))) {
                                            for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {

                                                final long getMessageKey = Long.parseLong(Objects.requireNonNull(chatDataSnapshot.getKey()));
                                                final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(requireContext(), getKey));

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
                                MessagesList_Model messagesListModel = new MessagesList_Model(finalFollow_model.getName(), finalFollow_model.getUsername(),
                                        lastMessage, finalFollow_model.getImageUrl(), chatKey, unseenMessage);
                                messagesListModels.add(messagesListModel);
                                messagesRecycleReview.setAdapter(new Patient_Messages_Adapter(messagesListModels, getContext()));
                                messagesAdapter.notifyDataSetChanged();
                                messagesAdapter.UpdateData(messagesListModels);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void ClearAll() {
        if (list != null) {
            list.clear();
        } else {
            Toast.makeText(getContext(), "The List is null", Toast.LENGTH_SHORT).show();
        }
        if (doctor_follow_adapter != null) {
            doctor_follow_adapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }

}