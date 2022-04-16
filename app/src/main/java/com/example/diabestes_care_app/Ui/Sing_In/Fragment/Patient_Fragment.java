package com.example.diabestes_care_app.Ui.Sing_In.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Home_Patient;

public class Patient_Fragment extends Fragment {

    Button log_in , sing_up;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in_p, container, false);
        log_in = view.findViewById(R.id.btn_log_in_fb);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Patient.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "Log In Success", Toast.LENGTH_SHORT).show();
            }
        });

        sing_up = view.findViewById(R.id.btn_sing_up_fb);
        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sing Up Success", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}