package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reports_Adapter extends RecyclerView.Adapter<Reports_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Reports_Model> list;
   // private final int limit = 7;
    String status_suger  , PatientUsername;

    public Reports_Adapter(Context context, ArrayList<Reports_Model> list) {
        this.context = context;
        this.list = list;
    }
// mohammed
    @NonNull
    @Override

    public Reports_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports, parent, false);
        return new Reports_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Reports_Adapter.MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.time.setText(list.get(position).getTime());
        holder.timesuger.setText(list.get(position).getTimesuger());

        //====================Initialize object from model & database reference=====================
        SharedPreferences prefs = this.context.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        DatabaseReference statuses_suger = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("حالة القياس");

        //============================Online/Offline read Status ===================================
        statuses_suger.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status_suger = dataSnapshot.getValue(String.class);
                //mario should decide what to do with linkers snooping status here e.g.
                if (status_suger == null  ) {
                    return;
                } else {
                    try {
                        if (status_suger.contentEquals("warning")) {
                            holder.success.setVisibility(View.VISIBLE);
                            holder.error.setVisibility(View.GONE);
                            holder.worning.setVisibility(View.GONE);
                        } else if (status_suger.contentEquals("success")) {
                            holder.success.setVisibility(View.GONE);
                            holder.error.setVisibility(View.GONE);
                            holder.worning.setVisibility(View.VISIBLE);
                        } else if (status_suger.contentEquals("error")) {
                            holder.success.setVisibility(View.GONE);
                            holder.error.setVisibility(View.VISIBLE);
                            holder.worning.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(context, "لا يوجد حالة ..", Toast.LENGTH_SHORT).show();
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
//        if (list.size() > limit) {
//            return limit;
//        } else {
//
//        }
        return list.size();
    }


    public void updateUsersList(ArrayList<Reports_Model> reports_models) {
        this.list = reports_models;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, timesuger, time;
        ImageView imageView;
        ImageView worning, error  , success;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Re_title);
            timesuger = itemView.findViewById(R.id.Re_timesuger);
            time = itemView.findViewById(R.id.Re_time);
            // **************************************************
            imageView = itemView.findViewById(R.id.Dl_patient_image);
            // container = itemView.findViewById(R.id.Dl_patient_container);
            worning = itemView.findViewById(R.id.worning);
            error = itemView.findViewById(R.id.error);
            success = itemView.findViewById(R.id.success);
        }
    }
}
