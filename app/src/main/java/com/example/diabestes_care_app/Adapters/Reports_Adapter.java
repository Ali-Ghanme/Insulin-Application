package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reports_Adapter extends RecyclerView.Adapter<Reports_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Reports_Model> list;
    // private final int limit = 7;
    String status_sugar, PatientUsername, statuses_monthly;

    public Reports_Adapter(Context context, ArrayList<Reports_Model> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override

    public Reports_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_recyle_layout, parent, false);
        return new Reports_Adapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Reports_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Reports_Model list2 = list.get(position);

        holder.title.setText(list2.getBloodSugar());
        holder.time.setText(list2.getTime());
        holder.time_sugar.setText(list2.getTimeSugar());

        //====================Initialize object from model & database reference=====================
        SharedPreferences prefs = this.context.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        DatabaseReference statuses_sugar = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي");

        //============================Online/Offline read Status ===================================
        Query query = statuses_sugar.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status_sugar = dataSnapshot.child("حالة القياس").getValue(String.class);
                //mario should decide what to do with linkers snooping status here e.g.
                if (list2.getStatusSugar() == null) {
                    return;
                } else {
                    try {
                        if (list2.getStatusSugar().contentEquals("success")) {

                            holder.status.setBackgroundResource(R.drawable.succsess);
                        } else if (list2.getStatusSugar().contentEquals("warning")) {
                            holder.status.setBackgroundResource(R.drawable.worning);
                        } else if (list2.getStatusSugar().contentEquals("error")) {
                            holder.status.setBackgroundResource(R.drawable.error);
                        } else {
                            Toast.makeText(context, "لا يوجد حالات جديدة  ..", Toast.LENGTH_SHORT).show();
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
        TextView title, time_sugar, time;
        ImageView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Re_title);
            time_sugar = itemView.findViewById(R.id.Re_timesuger);
            time = itemView.findViewById(R.id.Re_time);
            // container = itemView.findViewById(R.id.Dl_patient_container);
            status = itemView.findViewById(R.id.patient_status);

        }
    }
}
