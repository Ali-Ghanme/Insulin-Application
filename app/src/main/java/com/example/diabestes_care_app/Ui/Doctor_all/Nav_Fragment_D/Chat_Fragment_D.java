package com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Adapters.Doctor_Follow_Adapter;
import com.example.diabestes_care_app.Adapters.Doctor_Messages_Adapter;
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


public class Chat_Fragment_D extends Fragment {
    // Variables
    private RecyclerView messagesRecycleReview;
    // Variables
    String DoctorUsername;
    // Variables
    DatabaseReference myRef, myRef2;
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

    List<Follow_Model> list;
    // Adapter
    Doctor_Follow_Adapter doctor_follow_adapter;

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
        myRef2 = FirebaseDatabase.getInstance().getReference("doctor");
        GetFollowingPatient();

        //============================Defines SharedPreferences=====================================
        SharedPreferences prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Recycle Review Setup==========================================
        messagesRecycleReview.setHasFixedSize(true);
        messagesRecycleReview.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesAdapter = new Doctor_Messages_Adapter(messagesListModels, getContext());
        messagesRecycleReview.setAdapter(messagesAdapter);

        return view;
    }

    //============================Get profile Image Profile from Firebase database==================
    @Override
    public void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getActivity() == null) {
                    return;
                }
                String PatientImage = snapshot.child(DoctorUsername).child("User_Profile_Image").child("Image").child("mImageUrI").getValue(String.class);
                Glide.with(getActivity()).load(PatientImage).into(imageView);
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
                for (DataSnapshot sn : snapshot.child(DoctorUsername).child("Follow").getChildren()) {
                    follow_model = new Follow_Model();
                    follow_model.setUsername(sn.getKey());
                    follow_model.setType(sn.getKey());
                    Log.e("TAG", "This is the chat key " + follow_model.getUsername());


                    Follow_Model finalFollow_model = follow_model;
                    myRef.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int getChatCounts = (int) snapshot.getChildrenCount();

                            if (getChatCounts > 0) {
                                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                    chatKey = dataSnapshot1.getKey();

                                    if (dataSnapshot1.hasChild("patient_1") && dataSnapshot1.hasChild("doctor_2")
                                            && dataSnapshot1.hasChild("messages")) {

                                        final String getUserOne = dataSnapshot1.child("patient_1").getValue(String.class);
                                        final String getUserTow = dataSnapshot1.child("doctor_2").getValue(String.class);

                                        assert getUserOne != null;
                                        if ((getUserOne.equals(DoctorUsername) && Objects.equals(getUserTow, finalFollow_model.getUsername()))
                                                || (getUserOne.equals(finalFollow_model.getUsername()) && Objects.equals(getUserTow, DoctorUsername))) {
                                            for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {

                                                final long getMessageKey = Long.parseLong(Objects.requireNonNull(chatDataSnapshot.getKey()));
                                                final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTS(requireContext(), chatKey));

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
            Log.e("TAG", "The List is null");
        }
        if (doctor_follow_adapter != null) {
            doctor_follow_adapter.notifyDataSetChanged();
        }
        list = new ArrayList<>();
    }

}