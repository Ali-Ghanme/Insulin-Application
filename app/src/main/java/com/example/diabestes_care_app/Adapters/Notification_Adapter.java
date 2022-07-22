package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.Notification_Model;
import com.example.diabestes_care_app.R;

import java.util.ArrayList;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Notification_Model> list;

    public Notification_Adapter(Context context, ArrayList<Notification_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Notification_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        return new Notification_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notification_Adapter.MyViewHolder holder, int position) {
        // TextView
        holder.username.setText(list.get(position).getUsername());
        holder.time.setText(list.get(position).getTime());

        // ImageView : Glide Library
        Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username , time;
        ImageView userImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.Not_user_name2);
            time = itemView.findViewById(R.id.Not_time);
            userImage = itemView.findViewById(R.id.Not_user_profile_image);
        }
    }
}
