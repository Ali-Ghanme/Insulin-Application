package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care.Content.Content;


public class Instruction_Fragment extends Fragment {
    ListView listView;
    String[] items = {"تصفح"};

    // Hallow this is Update
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instracation_tab, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.activity_listview, items);
        listView = view.findViewById(R.id.listView_Instruction);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), Content.class);
            startActivity(intent);
        });

        return view;
    }
}