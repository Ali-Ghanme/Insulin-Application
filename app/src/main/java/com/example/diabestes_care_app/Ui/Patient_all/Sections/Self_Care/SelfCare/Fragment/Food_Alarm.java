package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.SelfCare.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content_f;


public class Food_Alarm extends Fragment {

    Button button;

    // Hallow this is Update
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__alram, container, false);

        button = view.findViewById(R.id.btn_food);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(Food_Alarm.this.getContext(), Content_f.class);
            Food_Alarm.this.startActivity(intent);
        });
        return view;
    }
}