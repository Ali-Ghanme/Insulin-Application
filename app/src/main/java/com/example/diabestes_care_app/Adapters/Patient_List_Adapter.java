package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.PatientList_Model;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Doctor_all.Patient_Profile_D;
import com.example.diabestes_care_app.Ui.Patient_all.Doctor_Profile_P;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Patient_List_Adapter extends RecyclerView.Adapter<Patient_List_Adapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<PatientList_Model> list;
    ArrayList<PatientList_Model> mDataFiltered;
    DatabaseReference myRef;
    String DoctorUsername;
    Dialog dialog;
    String snooping_status;

    public Patient_List_Adapter(Context context, ArrayList<PatientList_Model> list) {
        this.context = context;
        this.list = list;
        this.mDataFiltered = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //====================Initialize object from model & database reference=====================
        PatientList_Model list2 = mDataFiltered.get(position);
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(list2.getUsername());
        DatabaseReference follow = FirebaseDatabase.getInstance().getReference().child("doctor").child(DoctorUsername).child("Follow").child(list2.getUsername());

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
                        Log.e("TAG_Patient_List", e.getMessage());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //============================Follow/Unfollow read Status ==================================
        holder.follow_btn.setOnTouchListener(new View.OnTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(context.getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    Toast.makeText(context, "This is normal Press", Toast.LENGTH_SHORT).show();
                    follow.setValue("Following");
                    if (holder.follow_btn.getText().equals("متابعة")) {
                        dialog.show();
                    }
                    holder.follow_btn.setText("أتابع");
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    Toast.makeText(context, "Hallow this is Double Tap", Toast.LENGTH_SHORT).show();
                    follow.removeValue();
                    holder.follow_btn.setText("متابعة");
                    return super.onDoubleTap(e);
                }
            });

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
        follow_status(holder, follow);

        //============================Pass Data Patient ============================================
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Patient_Profile_D.class);
                intent.putExtra("Patient name", list2.getName());
                intent.putExtra("Patient_Pic_Profile", list2.getImageUrl());
                intent.putExtra("username", list2.getUsername());
                context.startActivity(intent);
            }
        });

        //============================Recycle Item data ============================================
        // TextView
        holder.name.setText(list.get(position).getName());
        holder.type.setText(list.get(position).getPatientType());
        // ImageView : Glide Library
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public void updateUsersList(ArrayList<PatientList_Model> PatientList_Model) {
        this.list = PatientList_Model;
        notifyDataSetChanged();
    }

    public void follow_status(MyViewHolder holder, DatabaseReference follow) {
        follow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String snooping_status1 = snapshot.getValue(String.class);
                if (snooping_status1 == null) {
                    return;
                } else {
                    //mario should decide what to do with linkers snooping status here e.g.
                    if (snooping_status1.contentEquals("Following")) {
                        holder.follow_btn.setText("أتابع");
                        //tell linker to stop doing sh*t
                    } else {
                        holder.follow_btn.setText("المتابعة");
                        //tell linker to do a lot of sh****t
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    mDataFiltered = list;
                } else {
                    ArrayList<PatientList_Model> lstFiltered = new ArrayList<>();
                    for (PatientList_Model row : list) {

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
                mDataFiltered = (ArrayList<PatientList_Model>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, type;
        ImageView imageView, img_off, img_on;
        RelativeLayout container;
        Button follow_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Pl_doctor_name);
            type = itemView.findViewById(R.id.Pl_doctor_username);
            imageView = itemView.findViewById(R.id.Pl_Doctor_image);
            container = itemView.findViewById(R.id.Pl_container);
            img_off = itemView.findViewById(R.id.Pl_img_off);
            img_on = itemView.findViewById(R.id.Pl_img_on);
            follow_btn = itemView.findViewById(R.id.Pl_follow_patient_d);

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