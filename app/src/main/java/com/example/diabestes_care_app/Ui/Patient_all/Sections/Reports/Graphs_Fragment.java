package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class Graphs_Fragment extends Fragment {
    TextView mohammed; // example
    // button week and day
    Button week, day;
    GraphView graph;
    String suger;
    int   suger_string ;
    // Firebase
    DatabaseReference databaseReference;
    String PatientUsername;
    public static final String MyPREFERENCES_P = "P_Username";
    public int dataa1, dataa2;

    // رسوم بيانية
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphs_, container, false);

        graph = view.findViewById(R.id.graphview);
        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي");

        Query query = databaseReference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                           suger = snapshot.child("نسبة السكر في الدم").getValue().toString();
                        Toast.makeText(getContext(), suger, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("TAG",e.getMessage());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });

       // Date currentTime = Calendar.getInstance().getTime();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        series.appendData(new DataPoint(0, 50), true, 5);
        series.appendData(new DataPoint(1, 150), true, 5);
        series.appendData(new DataPoint(2, 120), true, 5);
        series.appendData(new DataPoint(3, 200), true, 5);
        series.appendData(new DataPoint(4, 100), true, 5);

         //تفاصيل عن تسمية النقطة
        // series.setColor(Color.rgb(0, 80, 100));
        series.setColor(Color.RED);
        series.setTitle("نسبة السكر");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(2);
        graph.addSeries(series);
        //تسمية المحاور

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(50);
        graph.getViewport().setMaxY(250);
        return view;
    }


}