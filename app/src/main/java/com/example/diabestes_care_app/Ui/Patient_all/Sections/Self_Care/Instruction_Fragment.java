package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content;


public class Instruction_Fragment extends Fragment {
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instracation_tab, container, false);
        button = view.findViewById(R.id.btn_instraction);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(Instruction_Fragment.this.getContext(), Content.class);
            Instruction_Fragment.this.startActivity(intent);
        });

        return view;
    }
}