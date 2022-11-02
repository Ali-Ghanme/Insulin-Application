package com.example.diabestes_care_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Models.Reports_Monthly_Model;
import com.example.diabestes_care_app.R;

import java.util.ArrayList;

public class Reports_Monthly_Adapter extends RecyclerView.Adapter<Reports_Monthly_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Reports_Monthly_Model> list;
     private final int limit = 7;


    public Reports_Monthly_Adapter(Context context, ArrayList<Reports_Monthly_Model> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override

    public Reports_Monthly_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_monthly, parent, false);
        return new Reports_Monthly_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Reports_Monthly_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Reports_Monthly_Model list3 = list.get(position);
// Hallow
        // فحوصات الدهون
        holder.result_cholesterol.setText(list3.getResult_cholesterol());
        holder.result_triglycerid.setText(list3.getResult_triglycerid());
        holder.result_ldl.setText(list3.getResult_ldl());
        holder.result_hdl.setText(list3.getResult_hdl());

        // فحوصات  وظائف الكلى
        holder.result_creatinine.setText(list3.getResult_creatinine());
        holder.result_urea.setText(list3.getResult_urea());
        holder.result_uric.setText(list3.getResult_uric());

        // فحوصات مؤشر الكتلة
        holder.result_pressures.setText(list3.getResult_presser());
        holder.result_bmi_height.setText(list3.getResult_bmi_height());
        holder.result_bmi_weight.setText(list3.getResult_bmi_weight());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updateUsersList(ArrayList<Reports_Monthly_Model> reports_monthly_models) {
        this.list = reports_monthly_models;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView result_creatinine, result_urea, result_uric, result_cholesterol, result_triglycerid,
                result_ldl, result_hdl, result_pressures, result_bmi_height, result_bmi_weight;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            result_creatinine = itemView.findViewById(R.id.result_creatinine);
            result_urea = itemView.findViewById(R.id.result_urea);
            result_uric = itemView.findViewById(R.id.result_uric);
            result_cholesterol = itemView.findViewById(R.id.result_cholesterol);
            result_triglycerid = itemView.findViewById(R.id.result_triglycerid);
            result_ldl = itemView.findViewById(R.id.result_ldl);
            result_hdl = itemView.findViewById(R.id.result_hdl);
            result_pressures = itemView.findViewById(R.id.result_pressure);
            result_bmi_height = itemView.findViewById(R.id.result_bmi_height);
            result_bmi_weight = itemView.findViewById(R.id.result_bmi_weight);
        }
    }
}
