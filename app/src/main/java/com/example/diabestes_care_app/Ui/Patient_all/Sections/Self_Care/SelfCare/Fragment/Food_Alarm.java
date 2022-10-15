package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.SelfCare.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content_f;


public class Food_Alarm extends Fragment {
    ListView listView;
    String[] items = {"الفطور", "الغذاء", "العشاء", "الفطور في رمضان", "السحور في رمضان"};

    // Hallow this is Update
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food__alram, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.activity_listview, items);
        listView = view.findViewById(R.id.listView_lifeStyle_2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), Content_f.class);
            startActivity(intent);
        });
        return view;
    }
}