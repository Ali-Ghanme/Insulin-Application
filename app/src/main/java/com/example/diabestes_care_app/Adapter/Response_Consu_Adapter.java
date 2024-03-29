package com.example.diabestes_care_app.Adapter;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.diabestes_care_app.Ui.Patient_all.Doctor_Profile_P.MyPREFERENCES_PushKey;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.Private_Consu_Model;
import com.example.diabestes_care_app.NotificationSender.FcmNotificationsSender;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Response_Consu_Adapter extends RecyclerView.Adapter<Response_Consu_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Private_Consu_Model> list;
    Dialog dialog;
    String DoctorUsername, removeQuery;

    public Response_Consu_Adapter(Context context, ArrayList<Private_Consu_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    public Response_Consu_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consu_respons_recyle_layout, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull Response_Consu_Adapter.MyViewHolder holder, int position) {
        SharedPreferences prefs2 = context.getSharedPreferences(MyPREFERENCES_PushKey, MODE_PRIVATE);
        removeQuery = prefs2.getString("TAG_Push_Key", null);

        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        Private_Consu_Model list2 = list.get(position);
        DatabaseReference Consu_Response = FirebaseDatabase.getInstance().getReference("doctor").child(DoctorUsername).child("Consultation request").child("MSG");
        Query query = Consu_Response.orderByChild("PushKey").equalTo(list2.getPushKey());
        DatabaseReference general_Consolation = FirebaseDatabase.getInstance().getReference("Consultation request").child("MSG").push();

        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.consu_answer_dialog);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button oky = dialog.findViewById(R.id.ConsuA_btn_Reply);
        EditText Answer = dialog.findViewById(R.id.ConsuA_et_Answer);

        holder.response.setOnClickListener(v -> dialog.show());

        oky.setOnClickListener(v -> query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    String Consultation_Answer = Answer.getText().toString();
                    s.child("Doctor_Answer").getRef().setValue(Consultation_Answer);

                    general_Consolation.child("Title").setValue(list2.getConsuTitle());
                    general_Consolation.child("Subject").setValue(list2.getConsuSubject());
                    general_Consolation.child("Doctor_Image").setValue(list2.getDoctorImage());
                    general_Consolation.child("to").setValue(list2.getDoctor_name());
                    general_Consolation.child("PatientToken").setValue(list2.getPatientToken());
                    general_Consolation.child("Doctor_Answer").setValue(Consultation_Answer);

                    Toast.makeText(context, list2.getPatientToken(), Toast.LENGTH_SHORT).show();
                    try {
                        if (!list2.getPatientToken().isEmpty()) {
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(list2.getPatientToken(), "رد على استشارة", " قام الدكتور " +
                                    list2.getDoctor_name() + " برد على استشاراتك ستجدها في قسم الاستشارات العامة ", context.getApplicationContext());
                            notificationsSender.SendNotifications();
                        }
                    } catch (Exception exception) {
                        Log.e("TAG", exception.getMessage());
                    }

                    s.getRef().removeValue();
                    list.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        }));

        holder.reject.setOnClickListener(v -> {
            Toast.makeText(context, "Delete it", Toast.LENGTH_SHORT).show();
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot s : dataSnapshot.getChildren()) {
                        s.getRef().removeValue();
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled", databaseError.toException());
                }
            });
        });

        //============================Recycle Item data ============================================
        holder.Patient_Username.setText(list.get(position).getPatientName());
        holder.Consu_title.setText(list.get(position).getConsuTitle());
        holder.Consu_Que.setText(list.get(position).getConsuSubject());
        Glide.with(context).load(list.get(position).getPatientImage()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(holder.Patient_Image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUsersList(ArrayList<Private_Consu_Model> private_consu_models) {
        this.list = private_consu_models;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Patient_Image;
        TextView Patient_Username, Consu_title, Consu_Que;
        Button response, reject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Patient_Image = itemView.findViewById(R.id.Consu_Patient_Image_response);
            Patient_Username = itemView.findViewById(R.id.Consu_patient_username);
            Consu_title = itemView.findViewById(R.id.Consu_title_response);
            Consu_Que = itemView.findViewById(R.id.Consu_Que_response);
            response = itemView.findViewById(R.id.Consu_Accept_response);
            reject = itemView.findViewById(R.id.Consu_Reject_response);
        }
    }
}
