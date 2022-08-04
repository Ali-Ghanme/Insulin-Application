package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.Follow_Model;
import com.example.diabestes_care_app.R;

import java.util.List;

public class Doctor_Follow_Adapter extends RecyclerView.Adapter<Doctor_Follow_Adapter.MyViewHolder> {
    Context context;
    List<Follow_Model> list;
    String snooping_status;

    public Doctor_Follow_Adapter(Context context, List<Follow_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Doctor_Follow_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_following_list, parent, false);
        return new Doctor_Follow_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctor_Follow_Adapter.MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.type.setText(list.get(position).getType());
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateUsersList(List<Follow_Model> follow_models) {
        this.list = follow_models;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, type, status;
        ImageView imageView;
        ImageView img_off, img_on;
        RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Dl_doctor_name);
            type = itemView.findViewById(R.id.Dl_doctor_patient_type);
            imageView = itemView.findViewById(R.id.Dl_Doctor_image);
            container = itemView.findViewById(R.id.Dl_container);
            img_off = itemView.findViewById(R.id.Dl_img_off);
            img_on = itemView.findViewById(R.id.Dl_img_on);
            status = itemView.findViewById(R.id.Dl_follow_patient_d);

        }
    }
}
