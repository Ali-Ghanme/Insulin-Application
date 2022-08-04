package com.example.diabestes_care_app.Adapters;

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

import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.Models.Reports_Monthly_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

public class Reports_Monthly_Adapter extends RecyclerView.Adapter<Reports_Monthly_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Reports_Monthly_Model> list;
    // private final int limit = 7;


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
      holder.result_creatinine.setText(list3.getResult_creatinine());
      holder.result_urea.setText(list3.getResult_urea());
      holder.result_uric.setText(list3.getResult_uric());
      holder.result_cholesterol.setText(list3.getResult_cholesterol());
      holder.result_triglycerid.setText(list3.getResult_triglycerid());
      holder.result_ldl.setText(list3.getResult_ldl());
      holder.result_hdl.setText(list3.getResult_hdl());
      holder.result_pressuer.setText(list3.getResult_pressuer());
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
        TextView  result_creatinine  , result_urea  , result_uric  ,  result_cholesterol ,result_triglycerid   ,
                result_ldl  , result_hdl  , result_pressuer   , result_bmi_height  , result_bmi_weight    ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            result_creatinine = itemView.findViewById(R.id.result_creatinine);
            result_urea = itemView.findViewById(R.id.result_urea);
            result_uric = itemView.findViewById(R.id.result_uric);
            result_cholesterol = itemView.findViewById(R.id.result_cholesterol);
            result_triglycerid = itemView.findViewById(R.id.result_triglycerid);
            result_ldl = itemView.findViewById(R.id.result_ldl);
            result_hdl = itemView.findViewById(R.id.result_hdl);
            result_pressuer = itemView.findViewById(R.id.result_pressuer);
            result_bmi_height = itemView.findViewById(R.id.result_bmi_height);
            result_bmi_weight = itemView.findViewById(R.id.result_bmi_weight);



        }
    }
}
