package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Patient_all.Doctor_Profile_P.MyPREFERENCES_PushKey;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Doctor_Fragment.MyPREFERENCES_D;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.diabestes_care_app.Models.Private_Consu_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public Response_Consu_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consu_resons, parent, false);
        return new Response_Consu_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Response_Consu_Adapter.MyViewHolder holder, int position) {
        SharedPreferences prefs2 = context.getSharedPreferences(MyPREFERENCES_PushKey, MODE_PRIVATE);
        removeQuery = prefs2.getString("TAG_Push_Key", null);

        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        DoctorUsername = prefs.getString("TAG_NAME", null);

        Private_Consu_Model list2 = list.get(position);
        DatabaseReference Consu_Response = FirebaseDatabase.getInstance().getReference().child("doctor").child(DoctorUsername).child("Consultation request").child("MSG");

        holder.response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete it", Toast.LENGTH_SHORT).show();
                Consu_Response.child(removeQuery).removeValue();
            }
        });
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


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

    public void updateUsersList(ArrayList<Private_Consu_Model> private_consu_models) {
        this.list = private_consu_models;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
