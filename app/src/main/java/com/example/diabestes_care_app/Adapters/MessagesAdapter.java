package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.MessagesList;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.chat.Chat;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private ArrayList<MessagesList> messagesLists;
    private final Context context;

    public MessagesAdapter(ArrayList<MessagesList> messagesLists, Context context) {
        this.messagesLists = messagesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messaged_adapter_layout, parent, false);
        return new MessagesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MessagesList list2 = messagesLists.get(position);
        holder.Doctor_Image_Profile_Chat.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        // TextView
        holder.name.setText(messagesLists.get(position).getName());

        // ImageView : Glide Library
        Glide.with(context).load(messagesLists.get(position).getDoctorImage()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.Doctor_Image_Profile_Chat);

        if (list2.getUnseenMessages() == 0){
            holder.unseenMessages.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));

        }else {
            holder.unseenMessages.setVisibility(View.VISIBLE);
            holder.unseenMessages.setText(list2.getLastMessage() + "");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.text_input_pa));
        }
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat.class);
                intent.putExtra("username",list2.getUsername());
                intent.putExtra("name",list2.getName());
                intent.putExtra("profile_pic",list2.getDoctorImage());
                intent.putExtra("chat_key",list2.getChatKey());
                context.startActivity(intent);
            }
        });
    }
    public void UpdateData(ArrayList<MessagesList> messagesLists){
        this.messagesLists = messagesLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messagesLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView Doctor_Image_Profile_Chat;
        private final TextView name, lastMessage, unseenMessages;
        private final LinearLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Doctor_Image_Profile_Chat = itemView.findViewById(R.id.Chat_Image_Profile);
            name = itemView.findViewById(R.id.Chat_name);
            lastMessage = itemView.findViewById(R.id.Chat_lasMessage);
            unseenMessages = itemView.findViewById(R.id.Chat_unseenMessages);
            container = itemView.findViewById(R.id.root_layout);
        }
    }
}
