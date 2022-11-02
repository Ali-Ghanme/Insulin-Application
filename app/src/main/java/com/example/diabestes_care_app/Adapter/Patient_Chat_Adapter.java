package com.example.diabestes_care_app.Adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Doctor_all.Nav_Fragment_D.Home_Fragment_D.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Models.ChatList_Model;
import com.example.diabestes_care_app.R;

import java.util.List;

public class Patient_Chat_Adapter extends RecyclerView.Adapter<Patient_Chat_Adapter.MyViewHolder> {
    private List<ChatList_Model> chatListListModel;
    final Context context;
    SharedPreferences prefs;


    public Patient_Chat_Adapter(List<ChatList_Model> chatListListModel, Context context) {
        this.chatListListModel = chatListListModel;
        this.context = context;
    }

    @NonNull
    @Override
    public Patient_Chat_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Patient_Chat_Adapter.MyViewHolder holder, int position) {
        ChatList_Model list = chatListListModel.get(position);

        prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        String DoctorUsername = prefs.getString("TAG_NAME", null);

        if (list.getUsername().equals(DoctorUsername)) {
            holder.myMsgLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(list.getMessage());
            holder.oppoTime.setText(list.getDate() + "" + list.getTime());
        } else {
            holder.myMsgLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list.getMessage());
            holder.myTime.setText(list.getDate() + "" + list.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chatListListModel.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateChatList(List<ChatList_Model> chatListListModel) {
        this.chatListListModel = chatListListModel;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout oppoLayout;
        private final LinearLayout myMsgLayout;
        private final TextView oppoMessage;
        private final TextView myMessage;
        private final TextView oppoTime;
        private final TextView myTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myMsgLayout = itemView.findViewById(R.id.myMsgLayout);
            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMsgMessage);
            oppoTime = itemView.findViewById(R.id.oppoMsgTime);
            myTime = itemView.findViewById(R.id.myMSgTime);
        }
    }
}
