package com.example.diabestes_care_app.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

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

    @NonNull
    @Override
    public Doctor_Chat_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, null));
    }

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

    public void updateChatList(List<ChatList_Model> chatListListModel) {
        this.chatListListModel = chatListListModel;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout oppoLayout, myMsgLayout;
        private TextView oppoMessage, myMessage;
        private TextView oppoTime, myTime;

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
