package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.R;

import java.util.ArrayList;

public class Reports_Adapter extends RecyclerView.Adapter<Reports_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Reports_Model> list;

    public Reports_Adapter(Context context, ArrayList<Reports_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override

    public Reports_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports, parent, false);
        return new Reports_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Reports_Adapter.MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getData1());
        holder.content.setText(list.get(position).getData2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateUsersList(ArrayList<Reports_Model> reports_models) {
        this.list = reports_models;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            
            title = itemView.findViewById(R.id.Re_title);
            content = itemView.findViewById(R.id.Re_content);
        }
    }
}
