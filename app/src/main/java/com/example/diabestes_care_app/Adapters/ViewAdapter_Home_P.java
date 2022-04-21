package com.example.diabestes_care_app.Adapters;

import android.content.Context;
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

import com.example.diabestes_care_app.Models.Doctor_List_Modal_P;
import com.example.diabestes_care_app.R;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter_Home_P extends RecyclerView.Adapter<ViewAdapter_Home_P.HomeViewHolder> implements Filter.FilterListener {
    Context mContext;
    List<Doctor_List_Modal_P> mData;
    List<Doctor_List_Modal_P> mDataFiltered;

    public ViewAdapter_Home_P(Context mContext, List<Doctor_List_Modal_P> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public ViewAdapter_Home_P.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.doctors_item, parent, false);
        return new HomeViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter_Home_P.HomeViewHolder holder, int position) {

        holder.Doctor_img.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));

        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));
        holder.Doctor_Name.setText(mDataFiltered.get(position).getDoctor_Name());
        holder.Doctor_Bio.setText(mDataFiltered.get(position).getDoctor_Bio());
        holder.Starts_percent.setText(mDataFiltered.get(position).getDoctor_Stars());
        holder.Doctor_img.setImageResource(mDataFiltered.get(position).getDoctor_Image());
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public void onFilterComplete(int count) {

    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if (Key.isEmpty()) {
                    mDataFiltered = mData;
                } else {
                    List<Doctor_List_Modal_P> lstFiltered = new ArrayList<>();
                    for (Doctor_List_Modal_P row : mData) {

                        if (row.getDoctor_Name().toLowerCase().contains(Key.toLowerCase())) {
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
                mDataFiltered = (List<Doctor_List_Modal_P>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView Doctor_Name, Doctor_Bio, Starts_percent, like_percent;
        ImageView Doctor_img;
        RelativeLayout container;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            Doctor_Name = itemView.findViewById(R.id.doctor_name);
            Doctor_Bio = itemView.findViewById(R.id.sub_tiltel);
            Starts_percent = itemView.findViewById(R.id.starts_percent);
            Doctor_img = itemView.findViewById(R.id.Doctor_image);
        }
    }
}
