package com.example.diabestes_care_app.Ui.Patient_all.Sections.Self_Care;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.Content;
import com.example.diabestes_care_app.R;


public class Instruction_Fragment extends Fragment {
    ListView listView;
    String[] items = {"عن مرض السكري", "أنواع مرض السكري", "مضاعفات مرض السكري", "الفطور في رمضان", "السحور في رمضان", "ما هي غيبوبة السكر",
            "اعراض مبكرة لمرض السكري", "التعامل مع حالات اغماء السكري", "جرعات الانسولين وكيفية اخدها"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instracation_tab, container, false);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.activity_listview, items);
        listView = view.findViewById(R.id.listView_Instruction);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Content.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

// Instruction