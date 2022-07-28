package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Doctor_List_Adapter extends RecyclerView.Adapter<Doctor_List_Adapter.MyViewHolder> implements Filterable {
    Context context;
    List<DoctorList_Model> list;
    List<DoctorList_Model> mDataFiltered;
    String snooping_status;


    public Doctor_List_Adapter(Context context, List<DoctorList_Model> list) {
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

        //====================Initialize object from model & database reference=====================
        DoctorList_Model list2 = mDataFiltered.get(position);
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(list2.getUsername());

        //============================Online/Offline read Status ===================================
        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snooping_status = dataSnapshot.getValue(String.class);
                //mario should decide what to do with linkers snooping status here e.g.
                if (snooping_status == null) {
                    return;
                } else {
                    try {
                        if (snooping_status.contentEquals("online")) {
                            holder.img_off.setVisibility(View.GONE);
                            holder.img_on.setVisibility(View.VISIBLE);
                            //tell linker to stop doing sh*t
                        } else {
                            //tell linker to do a lot of sh****t
                            holder.img_off.setVisibility(View.VISIBLE);
                            holder.img_on.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", databaseError.getMessage());
            }
        });
        //============================Pass Data Patient ============================================
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Doctor_Profile_P.class);
                intent.putExtra("Doctor_name", list2.getName());
                intent.putExtra("Doctor_Pic_Profile", list2.getImageUrl());
                intent.putExtra("Doctor_username", list2.getUsername());
                intent.putExtra("Doctor_token", list2.getToken());
                context.startActivity(intent);
            }
        });

        //============================Recycle Item data ============================================
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

    public void updateUsersList(List<DoctorList_Model> DoctorList_Model) {
        this.list = DoctorList_Model;
        notifyDataSetChanged();
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
        ImageView img_off, img_on;
        RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Dl_patient_name);
            username = itemView.findViewById(R.id.Dl_patient_username);
            imageView = itemView.findViewById(R.id.Dl_patient_image);
            container = itemView.findViewById(R.id.Dl_patient_container);
            img_off = itemView.findViewById(R.id.img_patient_off);
            img_on = itemView.findViewById(R.id.img_patient_on);
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