package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.DoctorList_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Doctor_Profile_P;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorList_Adapter extends RecyclerView.Adapter<DoctorList_Adapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<DoctorList_Model> list;
    ArrayList<DoctorList_Model> mDataFiltered;

    public DoctorList_Adapter(Context context, ArrayList<DoctorList_Model> list, boolean isStatus) {
        this.context = context;
        this.list = list;
        this.mDataFiltered = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        DoctorList_Model list2 = mDataFiltered.get(position);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Doctor_Profile_P.class);
                intent.putExtra("Doctor name", list2.getName());
                intent.putExtra("Doctor_Pic_Profile", list2.getImageUrl());
                intent.putExtra("username",list2.getUsername());
                context.startActivity(intent);
            }
        });

        // TextView
        holder.name.setText(list.get(position).getName());
        holder.username.setText(list.get(position).getName());

        // ImageView : Glide Library
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    mDataFiltered = list;
                } else {
                    ArrayList<DoctorList_Model> lstFiltered = new ArrayList<>();
                    for (DoctorList_Model row : list) {

                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }
                    }
                    mDataFiltered = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (ArrayList<DoctorList_Model>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, username;
        ImageView imageView;
        RelativeLayout container;

        ImageButton follow_btn;
        DatabaseReference followRef;
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Dl_doctor_name);
            username = itemView.findViewById(R.id.Dl_doctor_username);
            imageView = itemView.findViewById(R.id.Dl_Doctor_image);
            container = itemView.findViewById(R.id.Dl_container);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            Toast.makeText(context, "Position" + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Doctor_Profile_P.class);
            context.startActivity(intent);
        }
    }
}