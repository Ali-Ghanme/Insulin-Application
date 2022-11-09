package com.example.diabestes_care_app.Adapter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

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

public class Doctor_Chat_Adapter extends RecyclerView.Adapter<Doctor_Chat_Adapter.MyViewHolder> {
    private List<ChatList_Model> chatListListModel;
    final Context context;
    SharedPreferences prefs;

    public Doctor_Chat_Adapter(List<ChatList_Model> chatListListModel, Context context) {
        this.chatListListModel = chatListListModel;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Doctor_Chat_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout_d, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Doctor_Chat_Adapter.MyViewHolder holder, int position) {
        ChatList_Model list = chatListListModel.get(position);

        prefs = context.getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        String patientUsername = prefs.getString("TAG_NAME", null);

        if (list.getUsername().equals(patientUsername)) {
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
            oppoLayout = itemView.findViewById(R.id.oppoLayout_d);
            myMsgLayout = itemView.findViewById(R.id.myMsgLayout_d);
            oppoMessage = itemView.findViewById(R.id.oppoMessage_d);
            myMessage = itemView.findViewById(R.id.myMsgMessage_d);
            oppoTime = itemView.findViewById(R.id.oppoMsgTime_d);
            myTime = itemView.findViewById(R.id.myMSgTime_d);
        }
    }
}
