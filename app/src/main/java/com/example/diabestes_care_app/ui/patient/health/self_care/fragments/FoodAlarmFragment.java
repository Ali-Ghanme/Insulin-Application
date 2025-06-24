package com.example.diabestes_care_app.ui.patient.health.self_care.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.patient.health.self_care.screens.ContentF;


public class FoodAlarmFragment extends Fragment {

    Button button;

    // Hallow this is Update
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.p_fragment_info_section, container, false);

        button = view.findViewById(R.id.btn_food);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(FoodAlarmFragment.this.getContext(), ContentF.class);
            FoodAlarmFragment.this.startActivity(intent);
        });
        return view;
    }
}