package com.example.diabestes_care_app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.diabestes_care_app.Models.PatientListModel;
import com.example.diabestes_care_app.Ui.Patient_all.Doctor_Profile_P;
import com.example.diabestes_care_app.Models.DoctorListModel;
import com.example.diabestes_care_app.R;

import java.util.ArrayList;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<DoctorListModel> list;
    ArrayList<DoctorListModel> mDataFiltered;
    boolean isChat;

    public DoctorListAdapter(Context context, ArrayList<DoctorListModel> list, boolean isChat) {
        this.context = context;
        this.list = list;
        this.mDataFiltered = list;
        this.isChat = isChat;
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
        // instuns new Mohammed Siam
        DoctorListModel Doctor = mDataFiltered.get(position);
        // TextView
        holder.name.setText(list.get(position).getName());
        holder.username.setText(list.get(position).getName());

        // ImageView : Glide Library
        Glide.with(context).load(list.get(position).getImageUrl()).placeholder(R.drawable.ic_user).error(R.drawable.notifications).into(holder.imageView);

        try {
// Chek , visible & gone user status mohammed siam
            if (isChat) {
                if (Doctor.getStatus().equals("online")) {
                    holder.img_on.setVisibility(View.VISIBLE);
                    holder.img_off.setVisibility(View.GONE);
                } else {
                    holder.img_on.setVisibility(View.GONE);
                    holder.img_off.setVisibility(View.VISIBLE);
                }
            }
                else {
                holder.img_on.setVisibility(View.GONE);
               holder.img_off.setVisibility(View.GONE);
         }
// End Chek , visible & gone user status mohammed siam
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), " حالة المستخدم غير مستقرة ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    mDataFiltered = list;
                } else {
                    ArrayList<DoctorListModel> lstFiltered = new ArrayList<>();
                    for (DoctorListModel row : list) {

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
                mDataFiltered = (ArrayList<DoctorListModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, username;
        ImageView imageView;
        RelativeLayout container;
        private ImageView img_off, img_on;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Dl_doctor_name);
            username = itemView.findViewById(R.id.Dl_doctor_username);
            imageView = itemView.findViewById(R.id.Dl_Doctor_image);
            container = itemView.findViewById(R.id.Dl_container);
            img_off = itemView.findViewById(R.id.img_off);
            img_on = itemView.findViewById(R.id.img_on);
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