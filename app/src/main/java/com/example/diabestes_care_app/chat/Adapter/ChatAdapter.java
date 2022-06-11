package com.example.diabestes_care_app.chat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.MemoryData.MemoryData;
import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.chat.Model.ChatList;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatList> chatListList;
    final Context context;
    private final String username;

    public ChatAdapter(List<ChatList> chatListList, Context context) {
        this.chatListList = chatListList;
        this.context = context;
        this.username = MemoryData.getData(context);
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        ChatList list = chatListList.get(position);
        if (list.getUsername().equals(username)) {
            holder.myMsgLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list.getMessage());
            holder.myTime.setText(list.getDate() + "" + list.getTime());

        } else {
            holder.myMsgLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(list.getMessage());
            holder.oppoTime.setText(list.getDate() + "" + list.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chatListList.size();
    }

    public void updateChatList(List<ChatList> chatListList) {
        this.chatListList = chatListList;
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
