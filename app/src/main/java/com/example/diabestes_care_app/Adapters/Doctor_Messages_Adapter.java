package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D.MyPREFERENCES_D;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.diabestes_care_app.Models.MessagesList_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.chat.Chat;
import com.example.diabestes_care_app.chat.Chat_D;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doctor_Messages_Adapter extends RecyclerView.Adapter<Doctor_Messages_Adapter.MyViewHolder> {

    // MessageList_Model Object
    private ArrayList<MessagesList_Model> messagesListModels;
    // Context
    private final Context context;
    SharedPreferences prefs;
    // Constructor
    public Doctor_Messages_Adapter(ArrayList<MessagesList_Model> messagesListModels, Context context) {
        this.messagesListModels = messagesListModels;
        this.context = context;
    }

    //==============================================================================================
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messaged_adapter_layout, parent, false);
        return new Doctor_Messages_Adapter.MyViewHolder(view);
    }

    //==============================================================================================
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        String DoctorUsername = prefs.getString("TAG_NAME", null);

        // Object from MessageList_Model
        MessagesList_Model list2 = messagesListModels.get(position);

        // Animation for the ImageView and Container
        holder.Doctor_Image_Profile_Chat.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        // Holder for TextView
        holder.name.setText(messagesListModels.get(position).getName());

        // Holder to ImageView : Glide Library
        Glide.with(context).load(messagesListModels.get(position).getDoctorImage()).placeholder(R.drawable.ic_user).error(R.drawable.notifications)
                .into(holder.Doctor_Image_Profile_Chat);

        // This if condition to see that last message and unseenMessage
        if (list2.getUnseenMessages() == 0) {
            holder.unseenMessages.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));
        } else {
            holder.unseenMessages.setVisibility(View.VISIBLE);
            holder.unseenMessages.setText(list2.getLastMessage() + "");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.text_input_pa));
        }

        // This is When Iam Click on the container that contain the user should go to the user chat screen
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // I need the User username that is login now to specified what is the message layout will show to him
                // What is the getUsername hold is hold the User username that patient click on recycle review
                if (list2.getUsername().equals(DoctorUsername)) {
                    Intent intent = new Intent(context, Chat.class);
                    intent.putExtra("username", list2.getUsername());
                    intent.putExtra("name", list2.getName());
                    intent.putExtra("profile_pic", list2.getDoctorImage());
                    intent.putExtra("chat_key", list2.getChatKey());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Chat_D.class);
                    intent.putExtra("username", list2.getUsername());
                    intent.putExtra("name", list2.getName());
                    intent.putExtra("profile_pic", list2.getDoctorImage());
                    intent.putExtra("chat_key", list2.getChatKey());
                    context.startActivity(intent);
                }
            }
        });
    }

    //=================================Update Data==================================================
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
