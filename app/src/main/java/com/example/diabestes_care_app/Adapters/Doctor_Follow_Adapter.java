package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.diabestes_care_app.Ui.Doctor_all.Secation.Follow.PatinetData.Patent_Report_d;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Doctor_Follow_Adapter extends RecyclerView.Adapter<Doctor_Follow_Adapter.MyViewHolder> {
    Context context;
    List<Follow_Model> list;
    String snooping_status;
    public static final String MyPREFERENCES_P_Username_D = "P_Username_D";
    SharedPreferences sharedpreferences;

    public Doctor_Follow_Adapter(Context context, List<Follow_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Doctor_Follow_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_following_recyle_layout, parent, false);
        return new Doctor_Follow_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctor_Follow_Adapter.MyViewHolder holder, int position) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES_P_Username_D, MODE_PRIVATE);
        Follow_Model list2 = list.get(position);

        // Online DataBase Reference
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(list2.getUsername());
        //============================Online/Offline read Status ===================================
        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snooping_status = dataSnapshot.getValue(String.class);
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

        //============================Online/Offline read Status ===================================
        FirebaseDatabase.getInstance().getReference("patient").child(list.get(position).getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.child("User_Profile_Image").child("Image").child("mImageUrI").getValue().toString();
                String diabetesType = snapshot.child("disease_info").child("Diabetes Type").getValue().toString();
                Glide.with(context).load(image).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);
                String name = snapshot.child("personal_info").child("name").getValue().toString();
                holder.name.setText(name);
                holder.type.setText(diabetesType);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //============================Pass Data Patient ============================================
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Patient_name_D", list2.getName());
                editor.putString("Patient_Pic_Profile_D", list2.getImageUrl());
                editor.putString("PatientUsername_D", list2.getUsername());
                editor.apply();

                Intent intent = new Intent(context, Patent_Report_d.class);
                context.startActivity(intent);
            }
        });
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
        TextView name, type;
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

        }
    }
}
