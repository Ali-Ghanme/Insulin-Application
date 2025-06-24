package com.example.diabestes_care_app.ui.doctor.components.adapters;

import static android.content.Context.MODE_PRIVATE;

import static com.example.diabestes_care_app.ui.auth.doctor.fragments.login_doctor_fragment.MyPREFERENCES_D;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.data.model.ChatList_Model_d;
import com.example.diabestes_care_app.R;

import java.util.List;

public class DoctorChatAdapter extends RecyclerView.Adapter<DoctorChatAdapter.MyViewHolder> {
    private List<ChatList_Model_d> chatListListModel;
    final Context context;
    SharedPreferences prefs;

    public DoctorChatAdapter(List<ChatList_Model_d> chatListListModel, Context context) {
        this.chatListListModel = chatListListModel;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public DoctorChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.d_chat_adapter_layout, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DoctorChatAdapter.MyViewHolder holder, int position) {
        ChatList_Model_d list = chatListListModel.get(position);

        prefs = context.getSharedPreferences(MyPREFERENCES_D, MODE_PRIVATE);
        String patientUsername = prefs.getString("TAG_NAME", null);

        Log.e("TAG", patientUsername + list.getUsername_d());
        if (list.getUsername_d().equals(patientUsername)) {
            holder.myMsgLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list.getMessage_d());
            holder.myTime.setText(list.getDate_d() + list.getTime_d());

        } else {

            holder.myMsgLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(list.getMessage_d());
            holder.oppoTime.setText(list.getDate_d() + list.getTime_d());
        }
    }

    @Override
    public int getItemCount() {
        return chatListListModel.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateChatList(List<ChatList_Model_d> chatListListModel) {
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
