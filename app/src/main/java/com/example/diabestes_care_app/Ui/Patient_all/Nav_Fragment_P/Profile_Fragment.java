package com.example.diabestes_care_app.Ui.Patient_all.Nav_Fragment_P;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Edit_Profile_P;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.Help;
import com.example.diabestes_care_app.Ui.Patient_all.Setting_P.notification;


public class Profile_Fragment extends Fragment {
    RelativeLayout profile_cont, notification_cont, DarkMode_cont, help_cont, LogOut_cont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_, container, false);
        profile_cont = view.findViewById(R.id.profile_cont);
        notification_cont = view.findViewById(R.id.notification_cont);
        DarkMode_cont = view.findViewById(R.id.DarkMode_cont);
        help_cont = view.findViewById(R.id.help_cont);
        LogOut_cont = view.findViewById(R.id.LogOut_cont);

        profile_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_Profile_P.class);
                startActivity(intent);
            }
        });
        notification_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), notification.class);
                startActivity(intent);
            }
        });
        DarkMode_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hallow Dark Mod is Unavailable right now 😉", Toast.LENGTH_SHORT).show();
            }
        });
        help_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Help.class);
                startActivity(intent);
            }
        });
        LogOut_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hallow LogOut is Unavailable right now 😉", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
}