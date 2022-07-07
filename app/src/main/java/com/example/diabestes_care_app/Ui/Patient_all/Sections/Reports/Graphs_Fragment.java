package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.jjoe64.graphview.GraphView;


public class Graphs_Fragment extends Fragment {
    TextView mohammed ; // example
    // button week and day
    Button week , day ;
   GraphView graphView;
    String Bold, Time;
   public Double  dataa1 , dataa2;
    // رسوم بيانية
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphs_, container, false);

        graphView = view.findViewById(R.id.graphview);
        day = view.findViewById(R.id.btn_day);
        week = view.findViewById(R.id.btn_week);
        mohammed = view.findViewById(R.id.mohammed);

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int r = 5 ;
                int w = 5;
                if ( r == w ) {

                    graphView.setVisibility(View.VISIBLE);
                    mohammed.setVisibility(View.GONE);
                } else {

                    graphView.setVisibility(View.GONE);
                    mohammed.setVisibility(View.GONE);
                }
            }
        });
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ww = 5 ;
                int qq = 5;
                if ( qq == ww ){

                    mohammed.setVisibility(View.VISIBLE);
                    graphView.setVisibility(View.GONE);


                } else {

                    mohammed.setVisibility(View.GONE);
                    graphView.setVisibility(View.GONE);
                }
            }
        });

//
//        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
//        Bold = prefs.getString("TAG_DATA_1", "1");
//        Time = prefs.getString("TAG_DATA_2", "2");
//
//        dataa1 = Double.parseDouble(Bold);
//        dataa2 = Double.parseDouble(Time);
//
//
////        Log.e("TAG", Bold + Time);
//        Toast.makeText(getContext(), "Test 11 : "+Bold + Time, Toast.LENGTH_SHORT).show();
////

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(0, 0),
//                new DataPoint(0, 0),
//                new DataPoint(3, 3),
//                new DataPoint(0, 0),
//                new DataPoint(0, 0)
//        });
//        graphView.addSeries(series);



        return view;
    }
}