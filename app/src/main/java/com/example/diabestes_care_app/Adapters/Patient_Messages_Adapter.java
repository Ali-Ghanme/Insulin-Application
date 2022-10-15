package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.MessagesList_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.chat.Chat;
import com.example.diabestes_care_app.chat.Chat_D;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Patient_Messages_Adapter extends RecyclerView.Adapter<Patient_Messages_Adapter.MyViewHolder> {

    // MessageList_Model Object
    private ArrayList<MessagesList_Model> messagesListModels;
    // Context
    private final Context context;
    SharedPreferences prefs;

    // Constructor
    public Patient_Messages_Adapter(ArrayList<MessagesList_Model> messagesListModels, Context context) {
        this.messagesListModels = messagesListModels;
        this.context = context;
    }

    //==============================================================================================
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_messaged_layout, parent, false);
        return new Patient_Messages_Adapter.MyViewHolder(view);
    }

    //==============================================================================================
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        prefs = context.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        String patientUsername = prefs.getString("TAG_NAME", null);

        // Object from MessageList_Model
        MessagesList_Model list2 = messagesListModels.get(position);

        holder.name.setText(messagesListModels.get(position).getName());
        Glide.with(context).load(messagesListModels.get(position).getDoctorImage()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);

        // This if condition to see that last message and unseenMessage
        if (list2.getUnseenMessages() == 0) {
            holder.unseenMessages.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));
        } else {
            holder.unseenMessages.setVisibility(View.VISIBLE);
            holder.unseenMessages.setText(list2.getLastMessage() + "");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.text_input_pa));
        }


        //============================Online/Offline read Status ===================================
        FirebaseDatabase.getInstance().getReference("doctor").child(list2.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list2.setDoctorImage(Objects.requireNonNull(snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue()).toString());
                list2.setName(Objects.requireNonNull(snapshot.child("personal_info").child("name_ar").getValue()).toString());
                Glide.with(context).load(list2.getDoctorImage()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);
                holder.name.setText(list2.getName());
                holder.lastMessage.setText(list2.getLastMessage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // This is When Iam Click on the container that contain the user should go to the user chat screen
        holder.container.setOnClickListener(v -> {
            // I need the User username that is login now to specified what is the message layout will show to him
            // What is the getUsername hold is hold the User username that patient click on recycle review
            Intent intent;
            if (list2.getUsername().equals(patientUsername)) {
                intent = new Intent(context, Chat_D.class);
            } else {
                intent = new Intent(context, Chat.class);
            }
            intent.putExtra("username", list2.getUsername());
            intent.putExtra("name", list2.getName());
            intent.putExtra("profile_pic", list2.getDoctorImage());
            intent.putExtra("chat_key", list2.getChatKey());
            context.startActivity(intent);
        });
    }

    //=================================Update Data==================================================
    @SuppressLint("NotifyDataSetChanged")
    public void UpdateData(ArrayList<MessagesList_Model> messagesListModels) {
        this.messagesListModels = messagesListModels;
        notifyDataSetChanged();
    }

    //==============================================================================================
    @Override
    public int getItemCount() {
        return messagesListModels.size();
    }

    //==============================================================================================
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imageView;
        private final TextView name, lastMessage, unseenMessages;
        private final LinearLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.Chat_Image_Profile);
            name = itemView.findViewById(R.id.Chat_name);
            lastMessage = itemView.findViewById(R.id.Chat_lasMessage);
            unseenMessages = itemView.findViewById(R.id.Chat_unseenMessages);
            container = itemView.findViewById(R.id.root_layout);
        }
    }
}
