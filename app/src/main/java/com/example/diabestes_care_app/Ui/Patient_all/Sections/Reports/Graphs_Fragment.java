package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;

import static android.content.Context.MODE_PRIVATE;


public class Graphs_Fragment extends Fragment {
    TextView mohammed; // example
    // button week and day
    Button week, day;
     GraphView graphView;
    String Bold, Time, P_Username, blood;
    // Firebase
    DatabaseReference myRef;
    public static final String MyPREFERENCES_P = "P_Username";
    public Double dataa1, dataa2;

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
                int r =2;

                if (r == 2) {
//                        week.setBackgroundColor().setTint(Color.parseColor("#212121"));
                    week.getBackground().setTint(Color.parseColor("#212121"));
                    graphView.setVisibility(View.VISIBLE);
                    mohammed.setVisibility(View.GONE);
//                    week.invalidate();
                } else {

                    graphView.setVisibility(View.GONE);
                    mohammed.setVisibility(View.GONE);
                }
            }
        });
        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ww = 5;

                if ( ww == 5) {
                    day.getBackground().setTint(Color.parseColor("#707070"));
                    mohammed.setVisibility(View.VISIBLE);
                    graphView.setVisibility(View.GONE);
//                    day.invalidate();

                } else {

                    mohammed.setVisibility(View.GONE);
                    graphView.setVisibility(View.GONE);
                }
            }
        });
        //============================Firebase======================================================
        myRef = FirebaseDatabase.getInstance().getReference();
        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        P_Username = prefs.getString("TAG_NAME", null);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blood = snapshot.child("patient").child(P_Username).child("Reports_info").child("فحص يومي").child("نسبة السكر في الدم").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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