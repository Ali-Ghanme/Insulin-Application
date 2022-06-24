package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.jjoe64.graphview.GraphView;


public class Graphs_Fragment extends Fragment {
    GraphView graphView;
    String Bold, Time;

    // رسوم بيانية
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphs_, container, false);
        //graphView = view.findViewById(R.id.graphview);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        Bold = prefs.getString("TAG_DATA_1", null);
        Time = prefs.getString("TAG_DATA_2", null);

        Log.e("TAG", Bold + Time);

        return view;
    }
}