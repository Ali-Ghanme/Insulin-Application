package com.example.diabestes_care_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.R;

public class Doctor_Follow_Adapter extends RecyclerView.Adapter<Doctor_Follow_Adapter.MyViewHolder> {
    @NonNull
    @Override
    public Doctor_Follow_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_following_list, parent, false);
        return new Doctor_Follow_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctor_Follow_Adapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
