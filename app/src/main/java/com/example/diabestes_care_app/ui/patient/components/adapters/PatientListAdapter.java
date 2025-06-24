package com.example.diabestes_care_app.ui.patient.components.adapters;

import static android.content.Context.MODE_PRIVATE;

import static com.example.diabestes_care_app.ui.auth.doctor.fragments.login_doctor_fragment.MyPREFERENCES_D;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.data.model.PatientList_Model;
import com.example.diabestes_care_app.data.notification.NotificationSender.FcmNotificationsSender;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.doctor.home.screens.PatientDetailsScreen;
import com.example.diabestes_care_app.ui.patient.home.screens.ConsultationsRequestScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// implements Filterable
public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.MyViewHolder> {
    Context context;
    ArrayList<PatientList_Model> list;
    DatabaseReference myRef;
    String DoctorUsername, doctor_name;
    Dialog dialog;
    String snooping_status;
    public static final String MyPREFERENCES_P_List = "D_P_Username";
    SharedPreferences sharedpreferences;

    public PatientListAdapter(Context context, ArrayList<PatientList_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p_recycle_layout, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES_P_List, MODE_PRIVATE);

        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.p_dialog_custom);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //====================Initialize object from model & database reference=====================
        PatientList_Model list2 = list.get(position);
        myRef = FirebaseDatabase.getInstance().getReference("doctor");
        DatabaseReference online_status_all_users = FirebaseDatabase.getInstance().getReference().child("online_statuses").child(list2.getUsername());
        DatabaseReference follow = FirebaseDatabase.getInstance().getReference().child("doctor").child(DoctorUsername).child("Follow").child(list2.getUsername()).child("Following");
        DatabaseReference follower = FirebaseDatabase.getInstance().getReference().child("patient").child(list2.getUsername()).child("Following By").child(DoctorUsername).child("Follower");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor_name = snapshot.child(DoctorUsername).child("personal_info").child("name_ar").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //============================Online/Offline read Status ===================================
        online_status_all_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snooping_status = dataSnapshot.getValue(String.class);
                //mario should decide what to do with linkers snooping status here e.g.
                if (snooping_status == null) {
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
            final GestureDetector gestureDetector = new GestureDetector(context.getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    follow.setValue(list2.getUsername());
                    follower.setValue(DoctorUsername);
                    if (holder.follow_btn.getText().equals("متابعة")) {
                        holder.follow_btn.setText("أتابع");
                        Toast.makeText(context, "لألغاء المتابعة اضغط مرتين", Toast.LENGTH_SHORT).show();
                        // Remove the item on remove/button click
//                        list.remove(holder.getAbsoluteAdapterPosition());
//                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
////                        notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), list.size());
                        try {
                            if (!list2.getToken().isEmpty()) {
                                FcmNotificationsSender notificationsSender = new FcmNotificationsSender(list2.getToken(), "متابعة", " قام الدكتور " +
                                        doctor_name + " بمتابعتك ", context.getApplicationContext());
                                notificationsSender.SendNotifications();
                            }
                        } catch (Exception exception) {
                            Log.e("TAG", exception.getMessage());
                        }
                    }

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("TAG_NAME", list2.getImageUrl());
                    editor.apply();

                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    follow.removeValue();
                    follower.removeValue();
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
        follow_status(holder, follow, list2);

        //============================Pass Data Patient ============================================
        holder.container.setOnClickListener(v -> {
            Intent intent = new Intent(context, PatientDetailsScreen.class);
            intent.putExtra("Patient name", list2.getName());
            intent.putExtra("Patient_Pic_Profile", list2.getImageUrl());
            intent.putExtra("username", list2.getUsername());
            context.startActivity(intent);
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
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUsersList(ArrayList<PatientList_Model> PatientList_Model) {
        this.list = PatientList_Model;
        notifyDataSetChanged();
    }

    public void follow_status(MyViewHolder holder, DatabaseReference follow, PatientList_Model list) {
        follow.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String snooping_status1 = snapshot.getValue(String.class);
                if (snooping_status1 == null) {
                } else {
                    //mario should decide what to do with linkers snooping status here e.g.
                    if (snooping_status1.contentEquals(list.getUsername())) {
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


    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(ArrayList<PatientList_Model> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
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
            Intent intent = new Intent(context, ConsultationsRequestScreen.class);
            context.startActivity(intent);
        }
    }

}